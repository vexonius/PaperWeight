package io.tstud.paperweight.Utils;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sdsmdg.harjot.crollerTest.Croller;
import com.sdsmdg.harjot.crollerTest.OnCrollerChangeListener;

import io.tstud.paperweight.Progress.ProgressViewModel;
import io.tstud.paperweight.R;


public class BottomSheet extends BottomSheetDialogFragment {

    private Croller croller;
    private TextView progressText;
    private ProgressViewModel mViewModel;
    private int progressCurrent, progressToUpdate;

    public static BottomSheet newInstance() {
        BottomSheet fragment = new BottomSheet();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(ProgressViewModel.class);

        mViewModel.getBookToUpdate().observe(getActivity(), bookWithStats -> progressCurrent = bookWithStats.getStats().getReadProgress());
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.book_progress_layout, null);
        dialog.setContentView(contentView);

        croller = (Croller) contentView.findViewById(R.id.croller);
        progressText = (TextView) contentView.findViewById(R.id.progressPercent);

        croller.setProgress(progressCurrent);
        progressText.setText(String.valueOf(progressCurrent));

        croller.setOnCrollerChangeListener(new OnCrollerChangeListener() {
            @Override
            public void onProgressChanged(Croller croller, int progress) {
                progressText.setText(progress + "%");
                progressToUpdate = progress;
            }

            @Override
            public void onStartTrackingTouch(Croller croller) {

            }

            @Override
            public void onStopTrackingTouch(Croller croller) {

            }
        });
    }

    @Override
    public void onStop(){
        mViewModel.updateBookProgress(progressToUpdate);
        super.onStop();
    }
}
