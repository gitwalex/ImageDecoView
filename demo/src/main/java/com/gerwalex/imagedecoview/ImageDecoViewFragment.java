package com.gerwalex.imagedecoview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gerwalex.imagedecoview.databinding.ImageDecoViewFragmentBinding;

public class ImageDecoViewFragment extends Fragment {

    private ImageDecoViewFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ImageDecoViewFragmentBinding.inflate(inflater);
        return binding.getRoot();
    }
}
