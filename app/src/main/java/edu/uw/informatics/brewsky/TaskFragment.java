package edu.uw.informatics.brewsky;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TaskFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "time";
    private static final String ARG_PARAM2 = "instructions";

    private double time;
    private String instructions;
    private ImageButton done;

    private GestureDetector gestureDetector;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskFragment.
     */
    public static TaskFragment newInstance(double param1, String param2) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            time = getArguments().getDouble(ARG_PARAM1);
            instructions = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        //Step
        TextView step = (TextView) view.findViewById(R.id.txt_step);
        step.setText(getString(R.string.step, container.getId()));

        //Instructions
        TextView textView = (TextView) view.findViewById(R.id.txt_task_instructions);
        textView.setText((CharSequence) instructions);

        //Time
        TextView timeView = (TextView) view.findViewById(R.id.txt_time);
        timeView.setText(getTime(time));

        //Time
        this.done = (ImageButton) view.findViewById(R.id.btn_task_done);
        this.done.setVisibility((container.getId() - 1 == ((RecipeInstructionsActivity) getActivity()).getCurrent()) ? View.VISIBLE : View.INVISIBLE);
        this.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentSwipe(container.getId());
            }
        });

        //Gestures
        gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener(){
            private float THRESHOLD_X = 200;

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if(Math.abs(e1.getX() - e2.getX()) > THRESHOLD_X){
                    mListener.onFragmentSwipe(container.getId());
                    return true;
                }
                return false;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
        return view;
    }

    public void setCurrent(){
        if(this.done != null) {
            this.done.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentSwipe(int id);
    }

    private String getTime(double sec){
        double min = time / 60;
        if(min > 0){  //More than a minute
            return String.format("%.0f min", min);
        } else {
            return String.format("%.0f sec", sec);
        }
    }

}
