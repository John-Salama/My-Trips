package com.example.mytrips.ui.home;

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
import com.example.mytrips.UpcomingTripsAdapter;
import com.example.mytrips.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    public UpcomingTripsAdapter adapter;
    private FragmentHomeBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView recyclerView = root.findViewById(R.id.upcoming_RecyclerView);
        adapter = new UpcomingTripsAdapter(DataManager.getInstance().getUpcomingDays(),getContext());
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