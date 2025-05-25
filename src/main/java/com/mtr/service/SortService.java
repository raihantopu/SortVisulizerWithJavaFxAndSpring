package com.mtr.service;

import static com.mtr.utility.Utility.CURRENT_LINE_COLOR;
import static com.mtr.utility.Utility.INITIAL_COLOR;
import static com.mtr.utility.Utility.I_INDEX_COLOR;
import static com.mtr.utility.Utility.SORTED_COLOR;
import static com.mtr.utility.Utility.sleep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mtr.StageListner;

import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;

@Service
public class SortService {
	private final DrawingService drawingService;
	
	public SortService(DrawingService drawingService, StageListner stageListner) {
		this.drawingService = drawingService;
	}
	
	public void bubbleSort(List<Line> lines, HBox hbox, int time) {
	    int n = lines.size();
	    boolean swapped;
	    System.out.println("starting sort");
	    for (int i = 0; i < n - 1; i++) {
	        swapped = false;
	        lines.get(i).setStroke(I_INDEX_COLOR);
	        
	        for (int j = 0; j < n - 1 - i; j++) {
	            lines.get(j).setStroke(CURRENT_LINE_COLOR);

	            if (Integer.parseInt(lines.get(j).getId()) > Integer.parseInt(lines.get(j + 1).getId())) {
	                Collections.swap(lines, j, j + 1);
	                lines.get(j + 1).setStroke(SORTED_COLOR);
	                swapped = true;
	            }
	            //lines.get(j).setStroke(INITIAL_COLOR);  // Reset color
	            if (swapped && (j % 5 == 0 || j == n - 2 - i)) {  // Ensure last update in each loop
	                Platform.runLater(() -> this.drawingService.reDraw(lines, hbox));
	                sleep(time);
	            }
	        }
	        
	        if (swapped) {  // Ensure final redraw before exiting outer loop
	            Platform.runLater(() -> this.drawingService.reDraw(lines, hbox));
	            sleep(time);
	        }
	        lines.get(i).setStroke(INITIAL_COLOR);
	        if (!swapped) break;  // Stop if already sorted
	    }
	}

	//Selection sort
	public void selectionSort(List<Line> lines, HBox hbox, int time) {
        int n = lines.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            lines.get(minIndex).setStroke(I_INDEX_COLOR);
            for (int j = i + 1; j < n; j++) {
                lines.get(j).setStroke(CURRENT_LINE_COLOR);
                if (Integer.parseInt(lines.get(j).getId()) < Integer.parseInt(lines.get(minIndex).getId())) {
                    minIndex = j;
                }
                Platform.runLater(() -> this.drawingService.reDraw(lines, hbox));
                sleep(time);
            }
            Line temp = lines.get(i);
            lines.set(i, lines.get(minIndex));
            lines.get(minIndex).setStroke(SORTED_COLOR);
            lines.set(minIndex, temp);
            Platform.runLater(() -> this.drawingService.reDraw(lines, hbox));
            sleep(time);
        }
        this.drawingService.changeToSortedColor(lines);
    }
	//Insertion sort
    public void insertionSort(List<Line> lines, HBox hbox, int time) {
        int n = lines.size();
        for (int i = 1; i < n; i++) {
            Line line = lines.get(i);
            int j = i - 1;
            while (j >= 0 && Integer.parseInt(lines.get(j).getId()) > Integer.parseInt(line.getId())) {
                lines.set(j + 1, lines.get(j));
                lines.get(j).setStroke(CURRENT_LINE_COLOR);
                Platform.runLater(() -> this.drawingService.reDraw(lines, hbox));
                sleep(time);
                j--;
            }
            lines.set(j + 1, line);
            lines.get(j + 1).setStroke(SORTED_COLOR);
            Platform.runLater(() -> this.drawingService.reDraw(lines, hbox));
            sleep(time);
        }
        this.drawingService.changeToSortedColor(lines);
    }
    //Quick sort
    public void quickSort(List<Line> lines, HBox hbox, int time) {
        quickSortHelper(lines, 0, lines.size() - 1, hbox, time);
        this.drawingService.changeToSortedColor(lines);
    }

    private void quickSortHelper(List<Line> lines, int low, int high, HBox hbox, int time) {
        if (low < high) {
            int pi = partition(lines, low, high, hbox, time);
            quickSortHelper(lines, low, pi - 1, hbox, time);
            quickSortHelper(lines, pi + 1, high, hbox, time);
        }
    }

    private int partition(List<Line> lines, int low, int high, HBox hbox, int time) {
        Line pivot = lines.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (Integer.parseInt(lines.get(j).getId()) < Integer.parseInt(pivot.getId())) {
                i++;
                Line temp = lines.get(i);
                lines.set(i, lines.get(j));
                lines.get(j).setStroke(CURRENT_LINE_COLOR);
                lines.set(j, temp);
                Platform.runLater(() -> this.drawingService.reDraw(lines, hbox));
                sleep(time);
            }
        }
        Line temp = lines.get(i + 1);
        lines.set(i + 1, lines.get(high));
        lines.get(high).setStroke(SORTED_COLOR);
        lines.set(high, temp);
        Platform.runLater(() -> this.drawingService.reDraw(lines, hbox));
        sleep(time);
        return i + 1;
    }
    //Merge sort
    public List<Line> mergeSort(List<Line> lines, HBox hbox, int time) {
        mergeSortHelper(lines, 0, lines.size() - 1, hbox, time);
        this.drawingService.changeToSortedColor(lines);
        return lines;
    }

    private void mergeSortHelper(List<Line> lines, int left, int right, HBox hbox, int time) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortHelper(lines, left, mid, hbox, time);
            mergeSortHelper(lines, mid + 1, right, hbox, time);
            merge(lines, left, mid, right, hbox, time);
        }
    }

    private void merge(List<Line> lines, int left, int mid, int right, HBox hbox, int time) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<Line> L = new ArrayList<>(lines.subList(left, mid + 1));
        List<Line> R = new ArrayList<>(lines.subList(mid + 1, right + 1));

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (Integer.parseInt(L.get(i).getId()) <= Integer.parseInt(R.get(j).getId())) {
                lines.set(k, L.get(i));
                L.get(i).setStroke(CURRENT_LINE_COLOR);
                i++;
            } else {
                lines.set(k, R.get(j));
                R.get(j).setStroke(CURRENT_LINE_COLOR);
                j++;
            }
            Platform.runLater(() -> this.drawingService.reDraw(lines, hbox));
            sleep(time);
            k++;
        }

        while (i < n1) {
            lines.set(k, L.get(i));
            L.get(i).setStroke(CURRENT_LINE_COLOR);
            i++;
            k++;
            Platform.runLater(() -> this.drawingService.reDraw(lines, hbox));
            sleep(time);
        }

        while (j < n2) {
            lines.set(k, R.get(j));
            R.get(j).setStroke(CURRENT_LINE_COLOR);
            j++;
            k++;
            Platform.runLater(() -> this.drawingService.reDraw(lines, hbox));
            sleep(time);
        }
    }


}
