package core.utils;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class TableViewManager<T> {

    private final TableView<T> tableView;
    private final ObservableList<T> data;
    private final FilteredList<T> filteredData;

    public TableViewManager(Node tableView) {
        this.tableView = (TableView<T>) tableView;
        this.data = FXCollections.observableArrayList();
        this.filteredData = new FilteredList<>(data);
        this.tableView.setItems(filteredData);
    }

    public void addColumn(String columnName, Function<T, Object> valueFunction) {
        TableColumn<T, Object> column = new TableColumn<>(columnName);
        column.setCellValueFactory(cellData -> {
            Object value = valueFunction.apply(cellData.getValue());
            return new ReadOnlyObjectWrapper<>(value);
        });
        tableView.getColumns().add(column);
    }

    public void addData(T item) {
        data.add(item);
    }

    public void addAllData(List<T> items) {
        data.addAll(items);
    }

    public void removeData(T item) {
        data.remove(item);
    }

    public void clearData() {
        data.clear();
    }

    public void filter(Predicate<T> predicate) {
        filteredData.setPredicate(predicate);
    }

    public void removeFilter() {
        filteredData.setPredicate(null);
    }
}
