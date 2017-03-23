package testdagger.gaige.com.testdagger2.cards;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import testdagger.gaige.com.testdagger2.R;
import testdagger.gaige.com.testdagger2.base.BaseActivity;

import static testdagger.gaige.com.testdagger2.cards.Config.SCALE_GAP;
import static testdagger.gaige.com.testdagger2.cards.Config.TRANS_Y_GAP;

/**
 * Created by gaige on 17/3/22.
 */

public class CardsActivity extends BaseActivity {
    private List<String> list = new ArrayList<>();
    private static RecyclerView recyclerView;
    private BaseQuickAdapter<String, BaseViewHolder> adapter;
    private float itemWidth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardview);
        list.add("http://pic9.nipic.com/20100906/1295091_134639124058_2.jpg");
        list.add("http://pic31.nipic.com/20130802/7487939_095437256000_2.jpg");
        list.add("http://pic.58pic.com/58pic/16/58/28/80M58PICTcs_1024.jpg");
        list.add("http://a4.att.hudong.com/35/64/01300000276819133197645554930.jpg");
        recyclerView = (RecyclerView) findViewById(R.id.ry);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        itemWidth = 0.68F * screenWidth;
        recyclerView.setLayoutManager(new MyLayoutManager(itemWidth));
        if (adapter == null) {
            adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_card, list) {
                @Override
                protected void convert(final BaseViewHolder helper, String item) {
                    ImageView imageView = (ImageView) helper.getView(R.id.tv_card);
                    helper.setText(R.id.tv_card1,list.indexOf(item)+"");
                    Glide.with(CardsActivity.this)
                            .load(item)
                            .crossFade()
                            .centerCrop()
                            .override((int) itemWidth, (int) (0.75f * itemWidth))
                            .into(imageView);
                }
            };
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        Config.initConfig(this);
        ItemTouchHelper.Callback callback = new MyCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                String remove = list.remove(viewHolder.getLayoutPosition());
                list.add(0, remove);
                adapter.notifyDataSetChanged();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public static abstract class MyCallback extends ItemTouchHelper.Callback {

        private int mDefaultSwipeDirs;

        private int mDefaultDragDirs;

        public MyCallback(int dragDirs, int swipeDirs) {
            mDefaultSwipeDirs = swipeDirs;
            mDefaultDragDirs = dragDirs;
        }

        /**
         * Updates the default swipe directions. For example, you can use this method to toggle
         * certain directions depending on your use case.
         *
         * @param defaultSwipeDirs Binary OR of directions in which the ViewHolders can be swiped.
         */
        public void setDefaultSwipeDirs(int defaultSwipeDirs) {
            mDefaultSwipeDirs = defaultSwipeDirs;
        }

        /**
         * Updates the default drag directions. For example, you can use this method to toggle
         * certain directions depending on your use case.
         *
         * @param defaultDragDirs Binary OR of directions in which the ViewHolders can be dragged.
         */
        public void setDefaultDragDirs(int defaultDragDirs) {
            mDefaultDragDirs = defaultDragDirs;
        }

        /**
         * Returns the swipe directions for the provided ViewHolder.
         * Default implementation returns the swipe directions that was set via constructor or
         * {@link #setDefaultSwipeDirs(int)}.
         *
         * @param recyclerView The RecyclerView to which the ItemTouchHelper is attached to.
         * @param viewHolder   The RecyclerView for which the swipe direction is queried.
         * @return A binary OR of direction flags.
         */
        public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return mDefaultSwipeDirs;
        }

        /**
         * Returns the drag directions for the provided ViewHolder.
         * Default implementation returns the drag directions that was set via constructor or
         * {@link #setDefaultDragDirs(int)}.
         *
         * @param recyclerView The RecyclerView to which the ItemTouchHelper is attached to.
         * @param viewHolder   The RecyclerView for which the swipe direction is queried.
         * @return A binary OR of direction flags.
         */
        public int getDragDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return mDefaultDragDirs;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(getDragDirs(recyclerView, viewHolder),
                    getSwipeDirs(recyclerView, viewHolder));
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            double swipeValue = Math.sqrt(dX * dX + dY * dY);
            double fraction = swipeValue / getThreshold(viewHolder);
            if (fraction > 1) {
                fraction = 1;
            }
            int childCount = recyclerView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = recyclerView.getChildAt(i);
                int level = childCount - i - 1;
                if (level > 0) {
                    child.setScaleX((float) (1.0F + SCALE_GAP * (float) level - fraction * SCALE_GAP));
                    child.setScaleY((float) (1.0F - SCALE_GAP * (float) level + fraction * SCALE_GAP));
                    child.setAlpha((float) (1.0F - SCALE_GAP * (float) level * 3F + fraction * SCALE_GAP));
                    child.setTranslationY((float) (TRANS_Y_GAP * level - fraction * TRANS_Y_GAP));
                } else if (level == 0) {
                    child.setAlpha((float) (1.0F - fraction));
                }
            }
        }

        public float getThreshold(RecyclerView.ViewHolder viewHolder) {
            return recyclerView.getWidth() * getSwipeThreshold(viewHolder);
        }

    }


}
