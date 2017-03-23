package testdagger.gaige.com.testdagger2.cards;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by gaige on 17/3/23.
 */

public class MyLayoutManager extends RecyclerView.LayoutManager {

    private float width;

    public MyLayoutManager(float width) {
        this.width = width;
    }

    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.detachAndScrapAttachedViews(recycler);
        int itemCount = this.getItemCount();
        if (itemCount >= 1) {
            int bottomPosition;
            if (itemCount < Config.MAX_SHOW_COUNT) {
                bottomPosition = 0;
            } else {
                bottomPosition = itemCount - Config.MAX_SHOW_COUNT;
            }

            for (int position = bottomPosition; position < itemCount; ++position) {
                View view = recycler.getViewForPosition(position);
                this.addView(view);
                this.measureChildWithMargins(view, 0, 0);
                int widthSpace = this.getWidth() - this.getDecoratedMeasuredWidth(view);
                int heightSpace = this.getHeight() - this.getDecoratedMeasuredHeight(view);
                this.layoutDecoratedWithMargins(view,
                        widthSpace / 2,
                        heightSpace / 2,
                        widthSpace / 2 + this.getDecoratedMeasuredWidth(view),
                        heightSpace / 2 + this.getDecoratedMeasuredHeight(view));
                int level = itemCount - position - 1;
                if (level > 0) {
                    view.setScaleX(1.0F + Config.SCALE_GAP * (float) level);
                    view.setScaleY(1.0F - Config.SCALE_GAP * (float) level);
                    view.setTranslationY((float) (Config.TRANS_Y_GAP * level));
                    view.setAlpha(1.0F - Config.SCALE_GAP * (float) level * 3F);
                } else {
                    view.setAlpha(1.0F);
                }
            }

        }
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {

        return new RecyclerView.LayoutParams((int)width, (int)(width*0.75F));
    }

}
