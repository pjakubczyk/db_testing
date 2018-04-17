package org.jakubczyk.dbtesting.screens;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.jakubczyk.dbtesting.BaseActivity;
import org.jakubczyk.dbtesting.data.entity.TodoEntity;
import org.jakubczyk.dbtesting.databinding.ActivityMainBinding;
import org.jakubczyk.dbtesting.databinding.TodoItemBinding;
import org.jakubczyk.dbtesting.screens.di.DaggerTodoListComponent;
import org.jakubczyk.dbtesting.screens.di.TodoListComponent;

import java.util.List;


public class TodoListActivity extends BaseActivity implements TodoContract.View {

    private TodoListComponent component;
    private TodoContract.Presenter presenter;
    private ActivityMainBinding binding;
    private TodoListAdapter todoListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.itemsList.setLayoutManager(new LinearLayoutManager(this));
        todoListAdapter = new TodoListAdapter(getLayoutInflater());
        binding.itemsList.setAdapter(todoListAdapter);

        component = DaggerTodoListComponent.builder().appComponent(appComponent).build();
        presenter = component.getPresenter();

        presenter.create(this);

        binding.addItem.setOnClickListener(v -> presenter.addNewItem(binding.newItemValue.getText().toString()));
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @Override
    public void showEntities(List<TodoEntity> todoEntities) {
        todoListAdapter.addEntities(todoEntities);
    }

    static class TodoListAdapter extends RecyclerView.Adapter<TodoItemViewHolder> {

        LayoutInflater layoutInflater;
        SortedList<TodoEntity> todoEntities = new SortedList<TodoEntity>(TodoEntity.class, new TodoItemCallback(this));

        public TodoListAdapter(LayoutInflater layoutInflater) {
            this.layoutInflater = layoutInflater;
        }

        @NonNull
        @Override
        public TodoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TodoItemBinding binding = TodoItemBinding.inflate(layoutInflater, parent, false);
            return new TodoItemViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull TodoItemViewHolder holder, int position) {
            holder.setItemValue(todoEntities.get(position).getValue());
        }

        @Override
        public int getItemCount() {
            return todoEntities.size();
        }

        public void addEntities(List<TodoEntity> newEntities) {
            todoEntities.addAll(newEntities);
        }
    }

    static class TodoItemViewHolder extends RecyclerView.ViewHolder {

        private TodoItemBinding binding;

        public TodoItemViewHolder(TodoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setItemValue(String newValue) {
            binding.todoItem.setText(newValue);
        }
    }

    static class TodoItemCallback extends SortedListAdapterCallback<TodoEntity> {

        public TodoItemCallback(RecyclerView.Adapter adapter) {
            super(adapter);
        }

        @Override
        public int compare(TodoEntity o1, TodoEntity o2) {
            return o1.getCreatedAt().compareTo(o2.getCreatedAt());
        }

        @Override
        public boolean areContentsTheSame(TodoEntity oldItem, TodoEntity newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areItemsTheSame(TodoEntity item1, TodoEntity item2) {
            return item1.getId().equals(item2.getId());
        }
    }

}
