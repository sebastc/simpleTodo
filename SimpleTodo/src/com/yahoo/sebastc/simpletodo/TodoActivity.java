package com.yahoo.sebastc.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {

	private File file;
	private List<String> todoItems;
	private ArrayAdapter<String> aTodoItems;
	private ListView lvItems;
	private EditText etNewItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);

		file = new File(getFilesDir(), "todo.txt");
		loadItems();
		
		etNewItem = (EditText) findViewById(R.id.etNewItem);
		lvItems = (ListView) findViewById(R.id.lvItems);

		aTodoItems = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, todoItems);
		lvItems.setAdapter(aTodoItems);
		
		setupLVItemsListener();
	}

	private void setupLVItemsListener() {
		OnItemLongClickListener listener = new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item,
					int position, long id) {
				removeItem(position);
				return false;
			}

		};
		lvItems.setOnItemLongClickListener(listener);
	}

	private void loadItems() { //
		try {
			todoItems = FileUtils.readLines(file);
		} catch (IOException e) {
			todoItems = new ArrayList<String>();
			e.printStackTrace(); // TODO replace by user feedback.
		}
	}

	private void saveItems() {
		File file = new File(getFilesDir(), "todo.txt");
		try {
			FileUtils.writeLines(file, todoItems);
		} catch (IOException e) {
			todoItems = new ArrayList<String>();
			e.printStackTrace(); // TODO replace by user feedback.
		}
	}

	private void removeItem(int position) {
		todoItems.remove(position);
		saveItems();
		aTodoItems.notifyDataSetChanged();
	}
	
	
	public void addItem(View view) {
		String newTaskName = etNewItem.getText().toString();
		aTodoItems.add(newTaskName);
		etNewItem.setText("");
		saveItems();
	}
}
