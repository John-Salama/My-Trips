package com.example.mytrips.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytrips.DataManager;
import com.example.mytrips.R;
import com.example.mytrips.TripDataManger;
import com.example.mytrips.UpcomingTripsAdapter;
import com.example.mytrips.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {
    public UpcomingTripsAdapter adapter;
    private FragmentSlideshowBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView recyclerView = root.findViewById(R.id.upcoming_RecyclerView_history);
        adapter = new UpcomingTripsAdapter(TripDataManger.getInstance().getHistory(),getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}