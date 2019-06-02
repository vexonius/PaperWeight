package io.tstud.paperweight.Activities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by etino7 on 03/06/2019.
 */
public class MainViewModel extends ViewModel {

    private MutableLiveData<Integer> currentPosition = new MutableLiveData<>();

    public MainViewModel(){
        currentPosition.setValue(1);
    }

    public MutableLiveData<Integer> getCurrentPosition(){
        return currentPosition;
    }

    public MutableLiveData<Integer> setCurrentPosition(){
        return currentPosition;
    }
}
