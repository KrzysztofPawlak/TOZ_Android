package com.intive.toz.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.intive.toz.R;
import com.intive.toz.data.DateFormatter;
import com.intive.toz.network.ApiClient;
import com.intive.toz.news.model.News;
import com.intive.toz.news_detail.view.NewsDetailActivity;
import com.intive.toz.petDetails.model.Comment;
import com.intive.toz.petDetails.view.PetDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type News adapter.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_COMMENT_HEADER = 0;
    private static final int TYPE_NEWS_HEADER = 3;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_COMMENT = 2;
    private static int headersCount;

    private List<News> newsList;
    private List<Comment> comments;
    private DateFormatter formatter;

    /**
     * Instantiates a new News adapter.
     */
    public NewsAdapter() {
        this.formatter = new DateFormatter();
    }

    /**
     * Sets news list.
     *
     * @param newsList the news list
     */
    public void setNewsList(final List<News> newsList) {
        this.newsList = newsList;
    }

    /**
     * Gets news list.
     *
     * @return the news list
     */
    public List<News> getNewsList() {
        return newsList;
    }

    /**
     * Sets comments.
     *
     * @param comments the comments
     */
    public void setComments(final List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * Gets comments.
     *
     * @return the comments
     */
    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        if (viewType == TYPE_COMMENT_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comments_header, parent, false);
            return new CommentsHeaderViewHolder(view);
        }

        if (viewType == TYPE_NEWS_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_header, parent, false);
            return new NewsHeaderViewHolder(view);
        }

        if (viewType == TYPE_COMMENT) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_comment, parent, false);
            return new CommentsViewHolder(view);
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_row, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int p) {
        if (holder instanceof NewsViewHolder) {
            final NewsViewHolder h = (NewsViewHolder) holder;
            final int position = p - (comments.size() + headersCount);

            h.titleTv.setText(newsList.get(position).getTitle());
            h.contentsTv.setText(newsList.get(position).getContents());
            h.dateTv.setText(formatter.convertToDate(newsList.get(position).getCreated()));

            Context context = h.newsIv.getContext();
            String url = newsList.get(position).getPhotoUrl();
            h.newsIv.setVisibility(View.GONE);

            if (url != null && !url.isEmpty()) {
                h.newsIv.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(ApiClient.API_DOMAIN + newsList.get(position).getPhotoUrl())
                        .centerCrop()
                        .error(R.drawable.ic_default_photo_dog)
                        .into(h.newsIv);
            }
        } else if (holder instanceof CommentsViewHolder) {
            final CommentsViewHolder h = (CommentsViewHolder) holder;
            final int position = p - 1;
            String author = comments.get(position).getAuthorName() + " " + comments.get(position).getAuthorSurname();
            h.author.setText(author);
            h.content.setText(comments.get(position).getContents());
            String date = "| " + formatter.convertToDate(comments.get(position).getCreated());
            h.date.setText(date);
        }
    }

    @Override
    public int getItemCount() {
        if (newsList == null && comments == null) {
            headersCount = 0;
            return 0;
        } else if (newsList == null) {
            headersCount = 1;
            return comments.size() + headersCount;
        } else if (comments == null || comments.size() == 0) {
            headersCount = 1;
            return newsList.size() + headersCount;
        } else {
            headersCount = 2;
            return newsList.size() + comments.size() + headersCount;
        }
    }

    @Override
    public int getItemViewType(final int position) {
        if (isPositionComment(position)) {
            return TYPE_COMMENT;
        }

        if (isPositionCommentsHeader(position)) {
            return TYPE_COMMENT_HEADER;
        }

        if (isPositionNewsHeader(position)) {
            return TYPE_NEWS_HEADER;
        }

        return TYPE_ITEM;
    }

    private boolean isPositionCommentsHeader(final int position) {
        return position == 0 && comments.size() != 0;
    }

    private boolean isPositionNewsHeader(final int position) {
        if (comments.size() != 0 && position == comments.size() + 1) {
            return true;
        } else if (comments.size() == 0 && position == 0) {
            return true;
        } else if (newsList.size() == 0) {
            return false;
        }
        return false;
    }

    private boolean isPositionComment(final int position) {
        return comments != null && position != 0 && position < comments.size() + 1;
    }

    /**
     * The type News view holder.
     */
    final class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * The Title tv.
         */
        @BindView(R.id.title_tv)
        TextView titleTv;

        /**
         * The Date tv.
         */
        @BindView(R.id.date_tv)
        TextView dateTv;

        /**
         * The Description tv.
         */
        @BindView(R.id.contents_tv)
        TextView contentsTv;

        /**
         * The News iv.
         */
        @BindView(R.id.news_iv)
        ImageView newsIv;

        /**
         * Instantiates a new News view holder.
         *
         * @param itemView the item view
         */
        private NewsViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            int position = getAdapterPosition() - (comments.size() + headersCount);
            String id = newsList.get(position).getId();
            Intent i = new Intent(v.getContext(), NewsDetailActivity.class);
            i.putExtra(NewsDetailActivity.ID, id);
            v.getContext().startActivity(i);
        }
    }

    /**
     * The type News header view holder.
     */
    final class NewsHeaderViewHolder extends RecyclerView.ViewHolder {

        /**
         * Instantiates a new News header view holder.
         *
         * @param itemView the item view
         */
        private NewsHeaderViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    final class CommentsHeaderViewHolder extends RecyclerView.ViewHolder {

        /**
         * Instantiates a new News header view holder.
         *
         * @param itemView the item view
         */
        private CommentsHeaderViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    final class CommentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * The Author.
         */
        @BindView(R.id.author_tv)
        TextView author;

        /**
         * The Date.
         */
        @BindView(R.id.date_tv)
        TextView date;

        /**
         * The Content.
         */
        @BindView(R.id.content_tv)
        TextView content;

        /**
         * Instantiates a new Comments view holder.
         *
         * @param itemView the item view
         */
        CommentsViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            int position = getAdapterPosition() - 1;

            String pet = comments.get(position).getPetUuid();
            Intent intent = new Intent(itemView.getContext(), PetDetailsActivity.class);
            intent.putExtra("petKey", pet);
            itemView.getContext().startActivity(intent);
        }
    }
}
