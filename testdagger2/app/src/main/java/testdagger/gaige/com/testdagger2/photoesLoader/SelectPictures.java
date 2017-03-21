package testdagger.gaige.com.testdagger2.photoesLoader;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import testdagger.gaige.com.testdagger2.R;
import testdagger.gaige.com.testdagger2.base.BaseActivity;
import testdagger.gaige.com.testdagger2.databinding.ContentSelectPicturesBinding;

public class SelectPictures extends BaseActivity implements PhotoClickListener {
    private ContentSelectPicturesBinding contentSelectPicturesBinding;
    private static final String TAG = "SelectPictures";
    private static final int REQUEST_CODE1 = 0X00000100;
    private static final int REQUEST_CODE2 = 0X00000101;
    private Uri imageUri;
    private SimpleDateFormat format = new SimpleDateFormat(
            "yyyy-MM-dd-HH-mm-ss-SSS");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentSelectPicturesBinding = DataBindingUtil.setContentView(this, R.layout.content_select_pictures);
        contentSelectPicturesBinding.setClickListener(this);
    }

    @Override
    public void clickAlbum() {
        Log.d(TAG, "clickAlbum");
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE2);
    }

    @Override
    public void clickTakePhoto() {
        Log.d(TAG, "clickTakePhoto");
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(getExternalCacheDir() + "/test/thumb/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir.getAbsolutePath() + "/"
                    + format.format(Calendar.getInstance().getTime()) + ".jpg");
            imageUri = Uri.fromFile(file);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CODE1);
        } else {
            Toast.makeText(this, "没有sd卡！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE1) {
            switch (resultCode) {
                case RESULT_OK:
                    Glide.with(this)
                            .load(imageUri)
                            .error(R.mipmap.ic_launcher)
                            .crossFade()
                            .override(400, 400)
                            .centerCrop()
                            .into(contentSelectPicturesBinding.photo);
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "拍照取消！", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this, "拍照失败！", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else if (requestCode == REQUEST_CODE2) {
            switch (resultCode) {
                case RESULT_OK:
//                    Glide.with(this)
//                            .load(data.getData())
//                            .error(R.mipmap.ic_launcher)
//                            .crossFade()
//                            .override(400, 400)
//                            .centerCrop()
//                            .into(contentSelectPicturesBinding.photo);
                    String imgPath = getHighVersionPathByUri(this,data.getData());
                    Toast.makeText(this, imgPath+"", Toast.LENGTH_SHORT).show();
                    Glide.with(this)
                            .load(new File(imgPath))
                            .error(R.mipmap.ic_launcher)
                            .crossFade()
                            .override(400, 400)
                            .centerCrop()
                            .into(contentSelectPicturesBinding.photo);
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(this, "拍照取消！", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this, "拍照失败！", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @SuppressLint("NewApi")
    public String getHighVersionPathByUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
