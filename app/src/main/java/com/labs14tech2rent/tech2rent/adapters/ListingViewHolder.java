package com.labs14tech2rent.tech2rent.adapters;

import android.view.View;
import androidx.annotation.NonNull;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.labs14tech2rent.tech2rent.databinding.ListingSingleItemBinding;
import com.labs14tech2rent.tech2rent.models.Listing;

public class ListingViewHolder extends SortedListAdapter.ViewHolder<Listing> {

    private final ListingSingleItemBinding binding;
    public ListingViewHolder(ListingSingleItemBinding binding, ListingAdapter.Listener listener) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @Override
    protected void performBind(@NonNull Listing item) {
        binding.setListing(item);

    }
}
