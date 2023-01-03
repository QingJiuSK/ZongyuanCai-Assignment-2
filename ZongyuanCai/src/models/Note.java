package models;

import utils.Utilities;
import utils.CategoryUtility;

import java.util.ArrayList;

import java.util.Objects;



public class Note {
    private String noteTitle = "No Title";
    private int notePriority = 1;
    private String noteCategory = "";


    public Note() {
        items = new ArrayList<>();
    }

    public Note(String noteTitle, int notePriority, String noteCategory) {
        this.noteTitle = Utilities.truncateString(noteTitle, 20);
        this.notePriority = Utilities.validRange(notePriority, 1, 5) ? notePriority : 1;
        this.noteCategory = CategoryUtility.isValidCategory(noteCategory) ? noteCategory : "";
    }

    public int getNotePriority() {
        return this.notePriority;
    }

    public int numberOfItems() {
        return items.size();

    }


    public boolean isValidIndex(int index) {

        return (index >= 0) && (index < items.size());

    }

    public void setNoteTitle(String noteTittle) {
        this.noteTitle = noteTittle;
    }

    public String toString() {
        return noteTitle + "Priority=" + notePriority + "Archived=" + Utilities.booleanToYN(isNoteArchived);

    }

    public String getNoteCategory() {
        return this.noteCategory;
    }

    public void setNoteCategory(String noteCategory) {
        this.noteCategory = noteCategory;
    }

    public void setNotePriority(int Priority) {
        this.notePriority = Priority;
    }

    public String getNoteTitle() {
        return this.noteTitle;
    }

    public void setNoteTittle(String noteTittle) {
        this.noteTitle = noteTittle;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return notePriority == note.notePriority
                && isNoteArchived == note.isNoteArchived
                && Objects.equals(noteCategory, note.noteTitle)
                && Objects.equals(noteTitle, note.noteCategory)
                && Objects.equals(items, note.items);
    }

    public boolean isNoteArchived() {
        return this.isNoteArchived;
    }

    boolean isNoteArchived = false;

    public void setNoteArchived(boolean noteArchived) {
        this.isNoteArchived = noteArchived;
    }


    public ArrayList<Item> items = new ArrayList<>();

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public boolean addItem(Item items) {
        return getItems().add(items);
    }

    public boolean checkNoteCompletionStatus() {
        int i = 0;
        if (items.isEmpty()) {
            return true;
        }
        for (int j = 0; j < items.size(); j++) {
            if (isNoteArchived) {
                i++;
            }
        }
        if (i < items.size()) {
            return false;
        }
        return true;
    }

    public Item findItem(int index) {
        if (isValidIndex(index)) {
            return items.get(index);
        } else {
            return null;
        }
    }

    public Item deleteItem(int index) {
        if (isValidIndex(index)) {
            return items.remove(index);
        } else {
            return null;
        }
    }


    public boolean updateItem(int index, String ItemDescription, boolean ItemCompleted) {
        Item foundItem = findItem(index);
        if (foundItem != null) {
            foundItem.setItemCompleted(ItemCompleted);
            foundItem.setItemDescription(ItemDescription);
            return true;
        }
        return false;
    }

    public String listItems() {
        String str = "";
        if (numberOfItems() > 0) {
            for (int i = 0; i < items.size(); i++) {
                str = str + i + ":" + items.get(i).toString() + "\n";
                if (items.equals("")) {
                    return "No items added";
                }
            }
        } else {
            str = "No items added";
        }
        return str;
    }
}