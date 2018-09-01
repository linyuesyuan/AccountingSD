package com.example.tku.accountingsd.ui.categories;


import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.tku.accountingsd.Adapter.CategoriesAdapter;
import com.example.tku.accountingsd.Adapter.CategoriesPickerAdapter;
import com.example.tku.accountingsd.DBHelper.CategoriesDBHelper;
import com.example.tku.accountingsd.DBHelper.ImageDBHelper;
import com.example.tku.accountingsd.R;
import com.example.tku.accountingsd.model.ImageData;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryEditFragment extends Fragment {

    private RecyclerView.LayoutManager mLayoutManager;
    private CategoriesDBHelper dbHelper;
    private CategoriesAdapter adapter;
    private ImageDBHelper imageDBHelper;
    private CategoriesPickerAdapter categoriesPickerAdapter;

    private EditText etTitle;
    private View mColorPickerView;
    private RecyclerView mRecyclerView;
    private Button mButton;

    long i;
    ImageData imageData;
    List<ImageData> mImageDataList = new LinkedList<>();

    public CategoryEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_category_edit, container, false);

        etTitle = (EditText) v.findViewById(R.id.etTitle);
        mColorPickerView = (View) v.findViewById(R.id.color_picker);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        mButton = (Button) v.findViewById(R.id.bt_createCategories);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.hasFixedSize();

        mColorPickerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorPickerDialogBuilder
                        .with(getActivity())
                        .setTitle("選擇顏色")
                        .initialColor(((ColorDrawable) mColorPickerView.getBackground()).getColor())
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setPositiveButton("選擇", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                mColorPickerView.setBackgroundColor(selectedColor);
                                Log.d("color", Integer.toString(selectedColor));
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        loadImageData();
        populateRecyclerView();

        return v;
    }

    private void loadImageData(){
        String[] imageName = getResources().getStringArray(R.array.image_name);
        i=1;
        for(String name:imageName){
            imageData = new ImageData();
            imageData.setName(name);
            imageData.setId(i);
            mImageDataList.add(imageData);
            i++;
        }
    }

    private void saveCategories(){
        String title = etTitle.getText().toString();
        Integer color = ((ColorDrawable) mColorPickerView.getBackground()).getColor();
    }

    private void populateRecyclerView() {
        adapter = new CategoriesAdapter(mImageDataList, getActivity(), mRecyclerView);
        mRecyclerView.setAdapter(adapter);
    }

}
