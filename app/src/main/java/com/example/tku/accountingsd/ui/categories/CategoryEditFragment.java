package com.example.tku.accountingsd.ui.categories;


import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.tku.accountingsd.Adapter.CategoriesAdapter;
import com.example.tku.accountingsd.Adapter.CategoriesPickerAdapter;
import com.example.tku.accountingsd.DBHelper.CategoriesDBHelper;
import com.example.tku.accountingsd.R;
import com.example.tku.accountingsd.model.Categories;
import com.example.tku.accountingsd.model.ImageData;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryEditFragment extends Fragment implements CategoriesPickerAdapter.OnItemClick{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private CategoriesDBHelper dbHelper;
    private CategoriesPickerAdapter categoriesPickerAdapter;

    private EditText etTitle;
    private RadioGroup radioGroup;
    private View mColorPickerView;
    private RecyclerView mRecyclerView;
    private Button mButton;

    String imageFile;


    int exp_inc;

    long i;
    ImageData imageData;
    List<ImageData> mImageDataList = new LinkedList<>();

    public CategoryEditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_category_edit, container, false);

        etTitle = (EditText) v.findViewById(R.id.etTitle);
        radioGroup = (RadioGroup) v.findViewById(R.id.radioGroup);
        mColorPickerView = (View) v.findViewById(R.id.color_picker);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        mButton = (Button) v.findViewById(R.id.bt_createCategories);

        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.expense:
                i = 0;
                break;
            case R.id.revenue:
                i = 1;
                break;
        }


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
                saveCategories();
                getFragmentManager().beginTransaction().replace(R.id.content_main, new CategoryFragment()).commit();
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
        Categories categories = new Categories(title, imageFile, color, exp_inc);
        dbHelper = new CategoriesDBHelper(getActivity());
        dbHelper.saveNewCategories(categories);
    }

    private void populateRecyclerView() {
        categoriesPickerAdapter = new CategoriesPickerAdapter(mImageDataList, getActivity(), mRecyclerView, CategoryEditFragment.this);
        mRecyclerView.setAdapter(categoriesPickerAdapter);
    }


    @Override
    public void onClick(String value) {
        imageFile = value;
    }
}
