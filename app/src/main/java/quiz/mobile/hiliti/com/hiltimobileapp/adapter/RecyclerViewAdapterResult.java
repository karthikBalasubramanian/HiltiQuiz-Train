/*
 * Copyright (C) 2015 Antonio Leiva
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package quiz.mobile.hiliti.com.hiltimobileapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import quiz.mobile.hiliti.com.hiltimobileapp.HiltiApplication;
import quiz.mobile.hiliti.com.hiltimobileapp.R;
import quiz.mobile.hiliti.com.hiltimobileapp.constants.Tags;
import quiz.mobile.hiliti.com.hiltimobileapp.constants.UrlEndpoints;
import quiz.mobile.hiliti.com.hiltimobileapp.json.Requestor;
import quiz.mobile.hiliti.com.hiltimobileapp.logging.Log;
import quiz.mobile.hiliti.com.hiltimobileapp.network.VolleySingleton;
import quiz.mobile.hiliti.com.hiltimobileapp.pojo.Question;
import quiz.mobile.hiliti.com.hiltimobileapp.pojo.Question;

public class RecyclerViewAdapterResult extends RecyclerView.Adapter<RecyclerViewAdapterResult.ViewHolder> /*implements View.OnClickListener*/ {


    private ArrayList<Question> items = new ArrayList<Question>();
    SharedPreferences sharedPreferences= HiltiApplication.getAppContext().getSharedPreferences(Tags.PREF_NAME, Context.MODE_PRIVATE);
    //private OnItemClickListener onItemClickListener;

    VolleySingleton volleySingleton;
    RequestQueue mRequestQueue;

    public RecyclerViewAdapterResult() {
        volleySingleton = VolleySingleton.getvSingletonInstance();
        mRequestQueue = volleySingleton.getmRequestQueue();
    }

    public void setViewModels(ArrayList<Question> viewModels) {
        this.items = viewModels;
        notifyDataSetChanged();
    }
    /*public RecyclerViewAdapter(List<ViewModel> items) {
        this.items = items;
    }*/

    /*public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }*/

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_recycler, parent, false);
        //v.setOnClickListener(this);  // start listening to the view
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.m("inside onBindViewHolder");
        Question item = items.get(position);
         //get viewholder position from the list of view holders
        Log.m("item text" + item.getText());
       // holder.text.setText(item.getText());

        holder.text.setText(item.getText());
        if(item.getCorrectAns().equalsIgnoreCase(item.getAnswerByUser())) {
            //holder.ImageView.setImageUrl(item.getImageRes(), mImageLoader);
            sendCorrectQuestions(item.getQid());
            holder.text.setBackgroundColor(0xFF68FF57);
        }
        else
        {

            holder.text.setBackgroundColor(0xFFFF664E);
        }
            holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

   /* @Override
    public void onClick(final View v) {
        // Give some time to the ripple to finish the effect
        if (onItemClickListener != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Question Qpojo =(Question) v.getTag();
                    onItemClickListener.onItemClick(v, (Question) v.getTag());
                }
            }, 200);
        }
    }*/

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        //public ImageView ImageView;
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            //ImageView = (ImageView) itemView.findViewById(R.id.networkImageView);
            text = (TextView) itemView.findViewById(R.id.qtext);
        }
    }

    /*public interface OnItemClickListener {

        void onItemClick(View view, Question Qpojo);

    }*/

    public void sendCorrectQuestions(int qid){
        String url = UrlEndpoints.API_SERVER+UrlEndpoints.ANSWERED_CORRECT_URL+UrlEndpoints.URL_CHAR_QUESTION+UrlEndpoints.Q_ID_PARAM_ANSWERED_+qid+UrlEndpoints.URL_CHAR_AMEPERSAND+UrlEndpoints.EMP_ID_PARAM_ANSWERED+sharedPreferences.getInt(Tags.EMP_ID,0);
        Log.m("url is "+url);
//        Requestor.answeredCorrectStringRequest(mRequestQueue, url,Tags.RESULT_TAG);
    }
}
