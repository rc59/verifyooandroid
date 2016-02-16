package com.software.exp.expapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.software.exp.expapp.Activities.SourceShapeActivity;
import com.software.exp.expapp.Logic.Consts;
import com.software.exp.expapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScreenSlidePageFragment extends Fragment {


    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    @Bind(R.id.imageLabel)
    ImageView imageLabel;
    @Bind(R.id.textLabel)
    TextView textLabel;
    @Bind(R.id.doneButton)
    Button doneButton;


    public static ScreenSlidePageFragment create(int pageNumber) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page, container, false);
        ButterKnife.bind(this, rootView);

        if (mPageNumber == 0){
            textLabel.setText(getString(R.string.instructions1));
            imageLabel.setImageResource(R.drawable.touchicon);
            doneButton.setVisibility(View.GONE);
        }

        else if (mPageNumber == 1){
            textLabel.setText(getString(R.string.instructions2));
            imageLabel.setImageResource(R.drawable.touchicon);
            doneButton.setVisibility(View.GONE);
        }

        else if (mPageNumber == 2){
            textLabel.setText(getString(R.string.instructions3));
            imageLabel.setImageResource(R.drawable.touchicon);
            doneButton.setVisibility(View.GONE);
        }

        else{
            textLabel.setText(getString(R.string.instructions4));
            imageLabel.setImageResource(R.drawable.touchicon);
            doneButton.setVisibility(View.VISIBLE);
        }
        return rootView;
    }

    @OnClick(R.id.doneButton)
    public void onClickDoneButton(Button button) {
        onClickOK();
    }

    private void onClickOK() {
        Toast.makeText(getActivity(), getString(R.string.pleaseWait), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(), SourceShapeActivity.class);
        String instruction = getActivity().getIntent().getStringExtra(Consts.INSTRUCTION);
        String username = getActivity().getIntent().getStringExtra(Consts.USERNAME);
        intent.putExtra(Consts.INSTRUCTION, instruction);
        intent.putExtra(Consts.USERNAME, username);
        startActivity(intent);
    }


    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}