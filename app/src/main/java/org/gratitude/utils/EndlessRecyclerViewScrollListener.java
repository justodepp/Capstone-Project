package org.gratitude.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import timber.log.Timber;

    public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
//    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

        /**
         * The total number of items in the dataset after the last load
         */
        private int mPreviousTotal = 0;
        /**
         * True if we are still waiting for the last set of data to load.
         */
        private boolean mLoading = true;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = recyclerView.getChildCount();
            int totalItemCount = recyclerView.getLayoutManager().getItemCount();
            int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

            if (mLoading) {
                if (totalItemCount > mPreviousTotal) {
                    mLoading = false;
                    mPreviousTotal = totalItemCount;
                }
            }
            int visibleThreshold = 1;
            if (!mLoading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + visibleThreshold)) {
                // End has been reached

                onLoadMore();

                mLoading = true;
            }
        }

        public void resetPreviousTotal(){
            mPreviousTotal = 0;
        }

        public int getPreviousTotal(){
            return mPreviousTotal;
        }

        public abstract void onLoadMore();

    }