package com.yahoo.sebastc.simpletodo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {

    private ArrayAdapter<String> arrayAdapter;
	private EditText etNewItem;
	private ListView lvItems;
	private List<String> todoItems;
	private File file;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
    	etNewItem = (EditText)findViewById(R.id.etNewItem);
    	lvItems = (ListView) findViewById(R.id.lvItems);
    	loadItems();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems); 
        lvItems.setAdapter(arrayAdapter);
        setupListListener();
    }

	private void setupListListener() {
		OnItemLongClickListener listener = new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item,
					int position, long id) {
				todoItems.remove(position);
				saveItems();
				arrayAdapter.notifyDataSetChanged();
				return false;
			}
		};
		lvItems.setOnItemLongClickListener(listener );
	}

	private void loadItems() {
		file = new File(getFilesDir(), "todo.txt"); //
		try {
			todoItems = FileUtils.readLines(file); //What's the default encoding ?
		} catch (IOException e) {
			todoItems = new ArrayList<String>(); 
			e.printStackTrace(); //TODO replace by user feedback.
		}
	}
	
	private void saveItems() {
		File file = new File(getFilesDir(), "todo.txt");
		try {
			FileUtils.writeLines(file, todoItems);
		} catch (IOException e) {
			e.printStackTrace(); //TODO replace by user feedback.
		}
	}
    
    public void addItem(View view) {
    	String newTaskName = etNewItem.getText().toString();
    	arrayAdapter.add(newTaskName);
    	etNewItem.setText("");
    	saveItems();
    }
}
