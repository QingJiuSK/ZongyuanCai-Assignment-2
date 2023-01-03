package controllers;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.Note;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class NoteAPI {
    public ArrayList<Note> notes=new ArrayList<>();


    public ArrayList<Note> getNotes(){return notes;}
    public NoteAPI() {
        notes = new ArrayList<Note>();
    }

     //add a note into list
    public boolean add(Note note) {
        return notes.add(note);
    }

     // checks that the index passed as a parameter is valid.
    public boolean archiveNote(int indexToarchive) {
        if (isValidIndex(indexToarchive)) {
            for (int i = 0; i < notes.get(indexToarchive).numberOfItems(); i++) {
                if (notes.get(indexToarchive).getItems().get(i).isItemCompleted()) {
                    return false;
                }
            }
            notes.get(indexToarchive).setNoteArchived(true);
            return true;
        } else {
            return false;
        }
    }

   //returns the number of notes stored in the notes ArrayList
    public int numberOfNotes() {
        return notes.size();
    }


    //check if the index is valid
    public boolean isValidIndex(int index) {
        return (index >= 0) && (index < notes.size());
    }

    //get a note by the index
    public Note findNote(int index) {
        if (isValidIndex(index)) {
            return notes.get(index);
           }
        return null;
    }

    //list Item by the category
    public String listItemStatusByCategory(String category) {
        if (notes.isEmpty()) {
            return "No notes stored";
        }
        String Todostr = "";
        String completedstr = "";

        for (int i = 0; i < notes.size(); i++) {
            if (!notes.get(i).checkNoteCompletionStatus()) {
                Todostr += i + ":" + notes.get(i).toString() + "\n";

            } else if (notes.get(i).checkNoteCompletionStatus()) {
                completedstr += i + ":" + notes.get(i).toString() + "\n";

            }
        }return "Enter the categoty [Home , Work , Hobby , Holiday , College]" + category  + "\n"
                + "Number Completed:" + numberOfArchivedNotes() + "\n"
                + completedstr + "\n"
                + "Number TODO" + numberOfActiveNotes() + "\n"
                + Todostr;

    }

    //adds up the number of Items stored on ALL the notes in the ArrayList where isItemCompleted is set to true.
    public int numberOfCompleteItems() {
        int j = 0;
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).checkNoteCompletionStatus()) {
                j = j + notes.get(i).numberOfItems();
            }
        }
        return j;

    }

    //print all the notes
    public String listAllNotes() {
        if (notes.isEmpty()) {
            return "No notes in the store";
        } else {
            String listAllnotes = "";
            for (int i = 0; i < notes.size(); i++) {
                listAllnotes += i + ": " + notes.get(i) + "\n";
            }
            return listAllnotes;
        }
    }

    //list the active notes
    public String listActiveNotes(){
        String str = "";
        for (int i = 0; i < notes.size(); i++){
            if (!notes.get(i).isNoteArchived()){
                str = str + i + ":" + notes.get(i) + "\n";
            }
        }
        if (str.equals("")){
            return "No active notes stored";
        }
        return str;
    }

    //list the archived notes
    public String listArchivedNotes(){
        String str = "";
        for (int i = 0; i < notes.size(); i++){
            if (notes.get(i).isNoteArchived()){
                str = str + i + ":" + notes.get(i) + "\n";
            }
        }
        if (str.equals("")){
            return "No archived notes stored";
        }
        return str;
    }


     //returns the number of ARCHIVED notes stored in the notes ArrayList
    public int numberOfArchivedNotes(){
        int m = 0;
        for (int i = 0; i < notes.size();i++){
            if (notes.get(i).isNoteArchived()){
                m=m+1;
            }
        }
        return m;
    }
     //returns the number of ACTIVE notes stored in the notes ArrayList
    public int numberOfActiveNotes(){
        int j = 0;
        for (int i = 0; i < notes.size();i++){
            if (notes.get(i).isNoteArchived()){
                j=j+1;
            }
        }
        return notes.size() - j;
    }



   //This method deletes an note at the index parameter (if that index exists) and returns the deleted note object
    public Note deleteNote(int indexToDelete) {
        if (isValidIndex(indexToDelete)) {
            return notes.remove(indexToDelete);
        }
        return null;
    }

    //This method should attempt to retrieve the note stored at the index number passed as a parameter
    public boolean updateNote(int indexToUpdate, String noteTitle, int notePriority, String noteCategory) {
        if (isValidIndex(indexToUpdate)) {

            Note foundNote = findNote(indexToUpdate);
            if (foundNote != null) {
                foundNote.setNoteCategory(noteCategory);
                foundNote.setNotePriority(notePriority);
                foundNote.setNoteTittle(noteTitle);
                return true;
               }
            }
            return false;
       }
  // checks that active notes are in the ArrayList

    public String archiveNotesWithAllItemsComplete() {
        String str = "";
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).checkNoteCompletionStatus()) {
                str = str + i + ":" + notes.get(i) + "\n";
                notes.get(i).setNoteArchived(true);
            }
        }
        if (str.equals("")) {str= "No active notes stored";}
        return str;
    }

    //search the special item by description
    public String searchItemByDescription(String search) {
        if (notes.isEmpty()) {
            return "No notes stored";
        }
        String str = "";
        for (int i = 0; i < notes.size(); i++) {
            for (int j = 0; j < notes.get(i).getItems().size(); j++) {
                if (notes.get(i).getItems().get(j).getItemDescription() == search) {
                    str = str + i + ":" + notes.get(i) + notes.get(i).getItems().get(j) + "\n";
                }
            }
        }
        if (str.equals("")) {
            return "No notes found for:";
        }
        return str;

    }

    //search the special notes by title
    public String searchNotesByTitle(String search){
        if (notes.isEmpty()){
            return "No notes stored";
        }
        String str = "";
        for (int i = 0; i < notes.size();i++){
            if (notes.get(i).getNoteTitle() == search){
                str = str+ i + ":" + notes.get(i) + "\n";
            }
        }
        if (str.equals("")) {
            return "No notes found for:";
        }
        return str;
    }

    //returns the number of notes that are stored for the category passed as a parameter
    public int numberOfNotesByCategory(String category){
        int j = 0;
        for (int i =0; i < notes.size(); i++){
            if (notes.get(i).getNoteCategory() == category){
                j++;
            }
        }
        return j;
    }
    //returns the number of notes that are stored for the priority passed as a parameter
    public int numberOfNotesByPriority(int priority){
        int j = 0;
        for (int i =0; i < notes.size(); i++){
            if (notes.get(i).getNotePriority() == priority){
                j++;
            }
        }
        return j;
    }
    //adds up the number of Items stored on ALL the notes in the ArrayList and returns it
    public int numberOfItems(){
        int j = 0;
        for (int i = 0 ; i < notes.size(); i++){
            j = j+notes.get(i).numberOfItems();
        }
        return j;
    }

    // adds up the number of Items stored on ALL the notes in the ArrayList where isItemCompleted is set to false
    public int numberOfTodoItems(){
        int k = numberOfItems() - numberOfCompleteItems();
        return k;
    }

    //list the special notes by category
    public String listNotesBySelectedCategory(String category){
        String str = "";

        if (notes.isEmpty()){
            return "No notes stored";
        }
        for (int i =0;i < notes.size();i++) {
            if (notes.get(i).getNoteCategory() == category) {
                str = str + notes.get(i).toString()+"\n";
            }
        }
        if (str.equals("")) {
            return "No notes with category";
        }
        return str;
    }
    //list the special notes by priority
    public String listNotesBySelectedPriority(int priority){
        String str = "";

        if (notes.isEmpty()){
            return "No notes stored";
        }
        for (int i =0;i < notes.size();i++) {
            if (notes.get(i).getNotePriority() == priority) {
                str =str + notes.get(i).toString()+"\n";
            }
        }
        if (str.equals("")) {
            return "No notes with priority";
        }
        return str;
    }

    //list the  items didn't do
    public String listTodoItems(){
        String str = "";
        if (notes.isEmpty()){
            return "No notes stored";
        }
        for (int i = 0; i < notes.size(); i++){
            if (!notes.get(i).checkNoteCompletionStatus()){
                str = str + notes.get(i).getNoteTitle() + notes.get(i).getItems().get(i).toString()+"\n";
            }
        }
        return str;
    }



    public void load() throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[] { Note.class };

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream is = xstream.createObjectInputStream(new FileReader("notes.xml"));
        notes = (ArrayList<Note>) is.readObject();
        is.close();
    }




    public void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("notes.xml"));
        out.writeObject(notes);
        out.close();
    }



}