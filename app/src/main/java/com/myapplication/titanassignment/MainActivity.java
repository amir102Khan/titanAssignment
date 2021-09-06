package com.myapplication.titanassignment;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.databinding.DataBindingUtil;
import com.myapplication.titanassignment.databinding.ActivityMainBinding;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
    View.OnLongClickListener {

  private ActivityMainBinding binding;
  private int id = 0;
  private ArrayList<CellInfo> cellInfoArrayList = new ArrayList<>();
  private CountDownTimer countDownTimer;
  private HashMap<Integer, CellInfo> cellInfoHashMap = new HashMap<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

    binding.btnCreateChess.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        int chessSize = Integer.parseInt(binding.edChessSize.getText().toString());
        createBoard(chessSize);
      }
    });
  }


  private void createBoard(int chessSize) {
    cellInfoArrayList.clear();
    cellInfoHashMap.clear();
    int color1 = getResources().getColor(R.color.purple_200);
    int color2 = getResources().getColor(R.color.teal_200);
    TextView textView;
    ConstraintLayout.LayoutParams lp;
    int idArray[][] = new int[chessSize][chessSize];
    ConstraintSet cs = new ConstraintSet();

    // Add our views to the ConstraintLayout.
    for (int iRow = 0; iRow < chessSize; iRow++) {
      for (int iCol = 0; iCol < chessSize; iCol++) {
        textView = new TextView(this);
        lp = new ConstraintLayout.LayoutParams(ConstraintSet.MATCH_CONSTRAINT,
            ConstraintSet.MATCH_CONSTRAINT);

        id = View.generateViewId();
        idArray[iRow][iCol] = id;
        textView.setId(id);
        textView.setOnLongClickListener(this);
        textView.setText(String.valueOf(0));
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(((iRow + iCol) % 2 == 0) ? color1 : color2);
        textView.setOnClickListener(this);

        binding.layoutBoard.addView(textView, lp);
      }
    }

    // Create horizontal chain for each row and set the 1:1 dimensions.
    // but first make sure the layout frame has the right ratio set.
    cs.clone(binding.layoutBoard);
    cs.setDimensionRatio(R.id.layout_chess, chessSize + ":" + chessSize);
    for (int iRow = 0; iRow < chessSize; iRow++) {
      for (int iCol = 0; iCol < chessSize; iCol++) {
        id = idArray[iRow][iCol];
        cellInfoHashMap.put(id, new CellInfo(false, id, false));
        cellInfoArrayList.add(new CellInfo(false, id, false));
        cs.setDimensionRatio(id, "1:1");
        if (iRow == 0) {
          // Connect the top row to the top of the frame.
          cs.connect(id, ConstraintSet.TOP, R.id.layout_chess, ConstraintSet.TOP);
        } else {
          // Connect top to bottom of row above.
          cs.connect(id, ConstraintSet.TOP, idArray[iRow - 1][0], ConstraintSet.BOTTOM);
        }
      }
      // Create a horiontal chain that will determine the dimensions of our squares.
      cs.createHorizontalChain(R.id.layout_chess, ConstraintSet.LEFT,
          R.id.layout_chess, ConstraintSet.RIGHT,
          idArray[iRow], null, ConstraintSet.CHAIN_PACKED);
    }

    cs.applyTo(binding.layoutBoard);
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  public void onClick(View view) {
    CellInfo cellInfo = getCellInfo(view.getId());
    TextView textView = findViewById(view.getId());
    if (!cellInfo.isCountdownStarted()) {
      startCountdown(textView, cellInfo.getCellNumber());
    } else {
      stopCountDown(cellInfo.getCellNumber());
    }

  }

  private CellInfo getCellInfo(int cellNumber) {
    CellInfo cellInfo = cellInfoHashMap.get(cellNumber);
    return cellInfo;
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  private void startCountdown(TextView chessCell, int cellNumber) {
    updateCellInfo(cellNumber, true);
    final int[] counter = {0};
    countDownTimer = new CountDownTimer(10000000, 1000) {
      @Override
      public void onTick(long l) {
        chessCell.setText(String.valueOf(counter[0]));
        counter[0]++;
      }

      @Override
      public void onFinish() {

        updateCellInfo(cellNumber, false);
      }

    };
    countDownTimer.start();
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  private void stopCountDown(int cellNumber) {
    countDownTimer.cancel();
    updateCellInfo(cellNumber, false);
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  private void updateCellInfo(int cellNumber, boolean isCountdownStarted) {
    cellInfoHashMap.get(cellNumber).setCountdownStarted(isCountdownStarted);

  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  public boolean onLongClick(View view) {
    CellInfo cellInfo = getCellInfo(view.getId());
    TextView textView = findViewById(view.getId());
    if (cellInfo.isCountdownStarted()) {
      stopCountDown(cellInfo.getCellNumber());
    }
    return true;
  }
}