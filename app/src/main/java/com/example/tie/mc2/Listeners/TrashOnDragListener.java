package com.example.tie.mc2.Listeners;

import android.app.Fragment;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.tie.mc2.Fragments.FragmentBoard;
import com.example.tie.mc2.R;

/**
 * Created by Tie on 18-Apr-18.
 */

public class TrashOnDragListener implements View.OnDragListener {
    private FrameLayout trash;
    FragmentBoard parentFragment;

    public TrashOnDragListener(FrameLayout trash, FragmentBoard parentFragment) {
        super();
        this.trash = trash;
        this.parentFragment = parentFragment;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        //Toast.makeText(getContext(), "on drag smeca",Toast.LENGTH_SHORT).show();
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                return true;

            case DragEvent.ACTION_DRAG_ENTERED:
                trash.setBackgroundResource(R.drawable.delete_view_full);
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                trash.setBackgroundResource(R.drawable.delete_view_empty);
                return true;
            case DragEvent.ACTION_DROP:
                // Dropped, reassign View to ViewGroup

                View deleteView = (View) event.getLocalState();
                ViewGroup owner = (ViewGroup) deleteView.getParent();
                if(owner.getId() == R.id.board_finished) {
                    owner.removeView(deleteView);
                    trash.setBackgroundResource(R.drawable.delete_view_empty);
                    parentFragment.removeViewFromList(deleteView);
                }
                return true;
        }
        return false;
    }
}
