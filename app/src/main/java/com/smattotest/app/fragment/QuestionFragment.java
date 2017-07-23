package com.smattotest.app.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smattotest.app.R;
import com.smattotest.lib.model.Question;
import com.smattotest.lib.requests.APIGetQuestionsAsyncTask;
import com.smattotest.lib.requests.DownloadImageAsyncTask;
import com.smattotest.lib.requests.responses.APIGetQuestionsResponse;
import com.smattotest.lib.utils.StringUtils;
import com.smattotest.lib.utils.image.ImageController;

import java.util.List;
import java.util.Random;

/**
 * Created by pasqualini on 11/18/16.
 */

public class QuestionFragment extends Fragment {

    private static final String TAG = QuestionFragment.class.getSimpleName();
    private SwipeRefreshLayout fragment_question__refreshlayout = null;
    private LinearLayout fragment_question__linearlayout_view = null;
    private ImageController imageController = null;

    public QuestionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_question, container, false);
        initComponent(rootView, savedInstanceState);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initListeners();
    }

    private void initComponent(final View view, @Nullable Bundle savedInstanceState) {
        this.imageController = new ImageController(getContext());
        fragment_question__refreshlayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_question__refreshlayout);
        fragment_question__linearlayout_view = (LinearLayout) view.findViewById(R.id.fragment_question__linearlayout_view);
    }

    private void initListeners() {
        fragment_question__refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getQuestions();
                showLoadingView(true);
            }
        });
    }

    public void showLoadingView(final boolean show) {
        fragment_question__refreshlayout.post(new Runnable() {
            @Override
            public void run() {
                fragment_question__refreshlayout.setRefreshing(show);
            }
        });
    }

    private void initData() {
        getQuestions();
    }

    private void initAdapter(List<Question> questionList) {
        //select one of questions which is valid
        Question question = getRandomItem(questionList);
        if (question != null) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.row_question, null);
            final TextView row_question__textview_username = (TextView) view.findViewById(R.id.row_question__textview_username);
            final TextView row_question__textview_text = (TextView) view.findViewById(R.id.row_question__textview_text);
            final ImageView row_question__imageview_image = (ImageView) view.findViewById(R.id.row_question__imageview_image);
            // bind view
            //name
            if (question.getUser() != null) {
                if (!StringUtils.isNullOrEmpty(question.getUser().getName()))
                    row_question__textview_username.setText(question.getUser().getName());
            }
            //text
            if (question.getData() != null) {
                if (!StringUtils.isNullOrEmpty(question.getData().getText()))
                    row_question__textview_text.setText(question.getData().getText());

                //image
                if (!StringUtils.isNullOrEmpty(question.getData().getUrl())) {
                    String fileName = question.getData().getUrl().substring(question.getData().getUrl().lastIndexOf('/') + 1, question.getData().getUrl().length());
                    if (!StringUtils.isNullOrEmpty(fileName)) {
                        Bitmap bitmap = imageController.getImageBitmap(fileName, 0);
                        if (bitmap != null)
                            row_question__imageview_image.setImageBitmap(bitmap);
                        else {
                            new DownloadImageAsyncTask(getContext(), question.getData().getUrl(), new DownloadImageAsyncTask.IDownloadImageListener() {
                                @Override
                                public void onPreExecute() {

                                }

                                @Override
                                public void onPostExecute(Exception e, Bitmap bitmapResult) {
                                    row_question__imageview_image.setImageBitmap(bitmapResult);
                                }
                            }).execute();
                        }
                    }
                }
            }
            fragment_question__linearlayout_view.addView(view);
        }
    }

    private Question getRandomItem(List<Question> questionList) {
        if (questionList != null && !questionList.isEmpty()) {
            Question question = questionList.get(new Random().nextInt(questionList.size()));
            while (question.getCreated() == 0 || question.getData() == null) {
                question = questionList.get(new Random().nextInt(questionList.size()));
            }
            return question;
        }
        return null;
    }

    private void getQuestions() {
        new APIGetQuestionsAsyncTask(getActivity(), new APIGetQuestionsAsyncTask.IGetCakesListener() {
            @Override
            public void onPreExecute() {
                if (getActivity() == null) return;
                showLoadingView(true);
            }

            @Override
            public void onPostExecute(Exception e, APIGetQuestionsResponse apiGetQuestionsResponse) {
                if (getActivity() == null) return;
                showLoadingView(false);
                if (e == null && apiGetQuestionsResponse.getQuestionList() != null && apiGetQuestionsResponse.getQuestionList().size() > 0) {
                    initAdapter(apiGetQuestionsResponse.getQuestionList());
                }
            }
        }).execute();
    }


}
