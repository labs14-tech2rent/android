package com.labs14tech2rent.tech2rent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedListAdapterCallback;
import com.labs14tech2rent.tech2rent.databinding.ListingSingleItemBinding;
import com.labs14tech2rent.tech2rent.models.Listing;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

import java.util.Comparator;

public class ListingAdapter extends SortedListAdapter<Listing> {


    public interface Listener{
        void onListingClicked(Listing listing);
    }
    private final Listener listener;

    public ListingAdapter(Context context, Comparator<Listing> comparator, Listener listener) {
        super(context, Listing.class, comparator);
        this.listener = listener;
    }


    @NonNull
    @Override
    protected ViewHolder<?extends Listing> onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {
        final ListingSingleItemBinding binding = ListingSingleItemBinding.inflate(inflater, parent, false);
        return new ListingViewHolder(binding, listener);
    }
}
