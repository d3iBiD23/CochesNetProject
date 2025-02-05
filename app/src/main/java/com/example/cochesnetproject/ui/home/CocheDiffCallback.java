package com.example.cochesnetproject.ui.home;

import androidx.recyclerview.widget.DiffUtil;
import java.util.List;

public class CocheDiffCallback extends DiffUtil.Callback {
    private final List<ElementoCoche> oldList;
    private final List<ElementoCoche> newList;

    public CocheDiffCallback(List<ElementoCoche> oldList, List<ElementoCoche> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newList != null ? newList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).id == newList.get(newItemPosition).id;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        ElementoCoche oldItem = oldList.get(oldItemPosition);
        ElementoCoche newItem = newList.get(newItemPosition);

        return oldItem.marca.equals(newItem.marca) &&
                oldItem.modelo.equals(newItem.modelo) &&
                oldItem.precio == newItem.precio &&
                oldItem.descripcion.equals(newItem.descripcion) &&
                oldItem.imageUrl.equals(newItem.imageUrl);
    }
}
