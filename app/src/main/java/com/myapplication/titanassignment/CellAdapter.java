package com.myapplication.titanassignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;

public class CellAdapter extends RecyclerView.Adapter<CellAdapter.MyViewHolder> {

  private ArrayList<CellInfo> cellInfos;

  public CellAdapter(ArrayList<CellInfo> cellInfos) {
    this.cellInfos = cellInfos;
  }

  @NonNull
  @NotNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View listItem = layoutInflater.inflate(R.layout.item_layout, parent, false);
    MyViewHolder myViewHolder = new MyViewHolder(listItem);
    return myViewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull @NotNull CellAdapter.MyViewHolder holder, int position) {
    holder.tvCell.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

      }
    });


  }

  @Override
  public int getItemCount() {
    return cellInfos.size();
  }

  class MyViewHolder extends RecyclerView.ViewHolder {
    TextView tvCell;
    public MyViewHolder(
        @NonNull @org.jetbrains.annotations.NotNull View itemView) {
      super(itemView);
      tvCell = itemView.findViewById(R.id.tv_cell);
    }
  }
}
