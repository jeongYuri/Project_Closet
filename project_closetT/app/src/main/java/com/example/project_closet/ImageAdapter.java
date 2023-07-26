package com.example.project_closet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    Context context;

    ArrayList<Model> list = new ArrayList<>();
    ImageView rec;

    private DatabaseReference mDatabase;

    public ImageAdapter(Context context, ArrayList<Model> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.list_item, parent, false);

//        View view2 = inflater.inflate(R.layout.list_item_x, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            Glide.with(context).load(list.get(position).getImageUrl()).into(holder.mImageView);// 추천딱지 있는 list_xml
            GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.width = layoutParams.height = 300;
            holder.itemView.requestLayout();


        // *


    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    // * 어댑터 내 커스텀 리스너 인터페이스 정의
    public interface OnItemClickListener {

        void onItemClick(View view, int pos);
    }

    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null;

    // OnItemClickListener 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }




    class MyViewHolder extends RecyclerView.ViewHolder {

//        TextView topView, bottomView, outerView, seasonView, customView;

        ImageView mImageView;
        ImageView mImageView2;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);



            mImageView = itemView.findViewById(R.id.image_info_view);
            //
            mImageView2 = itemView.findViewById(R.id.image_info_view_x);

            ImageView rec = itemView.findViewById(R.id.rec); // 추천딱지 이미지...

//            String userouter = "패딩" ;
//            Query dbquery = mDatabase.child("ClosetInmyHand").child("UserAccount").child("Qph478hjZSMNHu9KKf9BPKm0jd83").child("images").orderByChild("outer").equalTo(userouter);


//            rec.setVisibility(View.VISIBLE); // 여기서 이제 특정 값만 어떻게 보여지게 할 것인가..?
            

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        // click event
//                        Toast.makeText(view.getContext() , "클릭이 됐니?" , Toast.LENGTH_SHORT).show();
//                        Log.d("확인", "클릭 위치 확인" + pos);
                        mListener.onItemClick(view, pos);

                    }

//                    rec.setVisibility(View.VISIBLE); // 클릭하면 추천딱지 생김..ㅋㅋ

                }
            });


        }

    }


}