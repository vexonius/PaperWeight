package io.tstud.paperweight.Activities;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.tstud.paperweight.R;

/**
 * Created by etino7 on 03/06/2019.
 */
public class MainViewModel extends ViewModel {

    private MutableLiveData<Integer> currentPosition = new MutableLiveData<>();

    public MainViewModel() {

    }

    public MutableLiveData<Integer> getCurrentPosition() {
        if(currentPosition.getValue() == null)
            setCurrentPosition(R.id.home_item);

        return currentPosition;
    }

    public void setCurrentPosition(int position) {
        currentPosition.setValue(position);
    }


}
