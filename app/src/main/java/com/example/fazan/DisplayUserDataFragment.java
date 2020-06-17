package com.example.fazan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

public class DisplayUserDataFragment extends Fragment {

    View view;
    Action actiuneFragmentButtonApasat;

    public void setActiuneButtonFragmentApasat(Action actiuneButtonFragmentApasat){
        this.actiuneFragmentButtonApasat = actiuneButtonFragmentApasat;
    }

    public void changeTextInTextViewUsername(String text){
        final AppCompatTextView textViewUsername = view.findViewById(R.id.textViewUsername);
        textViewUsername.setText(text);
    }

    public void changeTextInTextViewScore(String text){
        final AppCompatTextView textViewScore = view.findViewById(R.id.textViewScore);
        textViewScore.setText(text);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.userdata_fragment, container, false);
        view.findViewById(R.id.buttonFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actiuneFragmentButtonApasat.perform();
            }
        });
        return view;
    }
}
