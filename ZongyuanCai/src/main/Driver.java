package main;

import controllers.NoteAPI;
import models.Item;
import models.Note;
import utils.ScannerInput;
import utils.Utilities;

public class Driver{
    private NoteAPI noteAPI = new NoteAPI();

    public static void main(String[] args) {
        new Driver();
    }

    public Driver() {
        runMenu();
    }

    private int mainMenu() {
        return ScannerInput.readNextInt("""
                ------------------------------------------------------------------
                |                     Note Keeper App                            |
                ------------------------------------------------------------------
                | NOTE MENU                                                      |
                |   1) Add a note                                                |
                |   2) List all notes (all,active,archived)                      |
                |   3) Update a note                                             |
                |   4) Delete a note                                             |
                |   5) Archive a note                                            |
                ------------------------------------------------------------------
                | ITEM MENU                                                      |
                |   6) Add an item to a note                                     |
                |   7) Update item description on a note                         |
                |   8) Delete an item from a note                                |
                |   9) Mark item as complete/todo                                |
                ------------------------------------------------------------------
                | REPORT MENU FOR NOTES                                          |
                |   10) All notes and their items (active & archived)            |
                |   11) Archive notes whose items are all complete               |
                |   12) All notes within a selected Category                     |
                |   13) All notes within a selected Priority                     |
                |   14) Search for all notes (by note title)                     |
                |                                                                |
                ------------------------------------------------------------------
                | REPORT MENU FOR ITEMS                                          |
                | 　15) All items that are todo (with note title)                 |
                | 　16) Overall number of items todo/complete                     |
                | 　17) Todo/complete items by specific Category             　   |
                | 　18) Search for all items (by item description )               |      　                                         
                ------------------------------------------------------------------
                | SETTINGS MENU                                                  |
                | 　20) save                                                      |            
                | 　21) Load                                                      |           
                | 　0) Exit  　　　　　　　　　　　　　　　　　　　　　　　　              |
                ------------------------------------------------------------------
                ==>>  """);
    }

    private void runMenu(){
        int option = mainMenu();

        while (option != 0){

            switch (option) {
                case 1 -> addNote();
                case 2 -> viewNotes();
                case 3 -> updateNote();
                case 4 -> deleteNote();
                case 5 -> archiveNote() ;
                case 6 -> addItemToNote();
                case 7 -> updateItemDescInNote();
                case 8 -> deleteItemFromNote();
                case 9 -> markCompletionOfItem();
                case 10-> printActiveAndArchivedReport();
                case 11-> archiveNotesWithAllItemsComplete();
                case 12-> printNotesBySelectedCategory();
                case 13-> printNotesByPriority();
                case 14-> searchNotesByTitle();
                case 15-> printAllTodoItems();
                case 16-> printOverallItemsTodoComplete();
                case 17-> printItemCompletionStatusByCategory();
                case 18-> searchItemsByDescription();
                case 20 -> save();
                case 21 -> load();
                default -> System.out.println("Invalid option entered: " + option);
            }

            //pause the program so that the user can read what we just printed to the terminal window
            ScannerInput.readNextLine("\nPress enter key to continue...");

            //display the main menu again
            option = mainMenu();
        }

        //the user chose option 0, so exit the program
        System.out.println("Exiting...bye");
        System.exit(0);
    }


    private void searchItemsByDescription(){
        String descr = ScannerInput.readNextLine("Enter the item description to search by:");
        noteAPI.searchItemByDescription(descr);
    }



    private void printItemCompletionStatusByCategory() {
        String category = ScannerInput.readNextLine("Enter the category :");
        for (int i = 0; i < noteAPI.numberOfNotes(); i++) {
            if (noteAPI.findNote(i).getNoteCategory() == category) {
                for (int j=0;j<noteAPI.findNote(i).numberOfItems();j++){
                   System.out.println(noteAPI.findNote(i).findItem(j).toString());
                }
            }
        }
    }


    private void printOverallItemsTodoComplete(){
        if(noteAPI.numberOfNotes()>0){
            System.out.println(noteAPI.numberOfCompleteItems());
            System.out.println(noteAPI.numberOfTodoItems());
        }else {
        System.out.println("No note");}
    }
    private void printAllTodoItems(){
        noteAPI.listTodoItems();
    }

    private void searchNotesByTitle(){
        if (noteAPI.numberOfNotes()>0){
            String title = ScannerInput.readNextLine("Enter the title:  ");
            noteAPI.searchNotesByTitle(title);
        }else {
        System.out.println("no Note");}
    }
    private void updateItemDescInNote(){
        printActiveNotes();
        int index = ScannerInput.readNextInt("Enter the Note number:  ");
        if (noteAPI.isValidIndex(index)){
            int indexToItem = ScannerInput.readNextInt("Enter the Item number :  ");
            String newDesc = ScannerInput.readNextLine("Enter the new description:  ");
            boolean iscompeleted = noteAPI.findNote(index).isNoteArchived();
            noteAPI.findNote(index).updateItem(indexToItem,newDesc,iscompeleted);
            System.out.println("update successfully");
        }else {
        System.out.println("Failed to update");}
    }

    private void deleteItemFromNote(){
        printActiveNotes();
        int indexTodelete = ScannerInput.readNextInt("Enter the Note number:  ");
        if (noteAPI.isValidIndex(indexTodelete)){
            int indexToItem = ScannerInput.readNextInt("Enter the Item number :  ");
            noteAPI.findNote(indexTodelete).deleteItem(indexToItem);
            System.out.println("delete successfully");
        }else {
        System.out.println("Failed to delete");}
    }


    private void markCompletionOfItem() {
        printActiveNotes();
        int index = ScannerInput.readNextInt("Enter the Note number:  ");
        if (noteAPI.isValidIndex(index)) {
            int indexTochange = ScannerInput.readNextInt("Enter the Note number:  ");
            char changeItem = ScannerInput.readNextChar("Need this item completed? (y/n): ");
            boolean yn = Utilities.YNtoBoolean(changeItem);
            noteAPI.findNote(index).findItem(indexTochange).setItemCompleted(yn);
            System.out.println("mark Successfully");
        }else {
          System.out.println("fail to mark");}
    }

    //gather the product data from the user and create a new product object - add it to the collection.
    private void addNote(){

        String noteName = ScannerInput.readNextLine("Enter the Note Name:  ");
        int notePriority = ScannerInput.readNextInt("Enter the Note Priority:  ");
        String noteCategory = ScannerInput.readNextLine("Enter the Note Category:  ");
        Note p = new Note(noteName,notePriority,noteCategory);

        boolean isAdded = noteAPI.add(p);
        if (isAdded){
            System.out.println("Note Added Successfully");
        }
        else{
            System.out.println("No Note Added");
        }
    }

     private void printActiveAndArchivedReport(){
       printActiveNotes();
       printArchivedNotes();
     }

     private void archiveNotesWithAllItemsComplete(){
        noteAPI.archiveNotesWithAllItemsComplete();
     }

    private void printNotesBySelectedCategory() {
        if (noteAPI.numberOfNotes() > 0) {
            String noteCategory = ScannerInput.readNextLine("Enter the Note Category:  ");
            noteAPI.listNotesBySelectedCategory(noteCategory);
        }else {
        System.out.println("No Note");}
    }

    private void printNotesByPriority(){
        if (noteAPI.numberOfNotes()>0){
            int notePriority = ScannerInput.readNextInt("Enter the Note Priority:  ");
            noteAPI.listNotesBySelectedPriority(notePriority);
        }else {
        System.out.println("no Note");}
    }



    private void viewNotes(){
        int option = ScannerInput.readNextInt("  ------------------------------------------------------------------\n" +
                "                |   1) View ALL notes                                            |\n" +
                "                |   2) View ACTIVE notes                                         |\n" +
                "                |   3) View ACTIVED notes                                        | \n" +
                "                ------------------------------------------------------------------ \n" +
                "| ==>>");
        while (option !=0){

            switch (option) {
                case 1 -> printAllnotes();
                case 2 -> printActiveNotes();
                case 3 -> printArchivedNotes();


                default -> System.out.println( "Invalid option entered: " + option);
            }

        }
        if (noteAPI.numberOfNotes()==0){
            System.out.println("No notes");
        }
    }
    private void printAllnotes(){
        System.out.println("List of Products are:");
        System.out.println(noteAPI.listAllNotes());
    }


    private void printActiveNotes(){
        System.out.println(noteAPI.listActiveNotes());
    }
    private void printArchivedNotes(){

        System.out.println(noteAPI.listArchivedNotes());
    }

    private void  archiveNote(){
        printActiveNotes();
        if (noteAPI.numberOfActiveNotes() != 0){
            int indexToarchived = ScannerInput.readNextInt("Enter the index of the note to archived");

            if (noteAPI.archiveNote(indexToarchived)){
                System.out.println("Archive successfully");
            }else {
                System.out.println("Can't archived");
            }
        }
    }



    //ask the user to enter the index of the object to delete, and assuming it's valid, delete it.
    private void deleteNote(){
        printAllnotes();
        if (noteAPI.numberOfNotes() > 0){
            //only ask the user to choose the product to delete if products exist
            int indexToDelete = ScannerInput.readNextInt("Enter the index of the product to delete ==> ");
            //pass the index of the product to controllers.Store for deleting and check for success.
            Note noteToDelete = noteAPI.deleteNote(indexToDelete);
            if (noteToDelete != null){
                System.out.println("Delete Successful! Deleted product: " + noteToDelete.getNoteTitle());
            }
            else{
                System.out.println("Delete NOT Successful");
            }
        }
    }


    //ask the user to enter the index of the object to update, and assuming it's valid,
    //gather the new note data from the user and update the selected product object.
    private void updateNote(){
        printAllnotes();
        if (noteAPI.numberOfNotes() > 0){
            //only ask the user to choose the note to update if note  exist
            int indexToUpdate = ScannerInput.readNextInt("Enter the index of the product to update ==> ");
            if (noteAPI.isValidIndex(indexToUpdate)){
                String Notetitle = ScannerInput.readNextLine("Enter the Note title:  ");
                int  NotePriority= ScannerInput.readNextInt("Enter the Note notePriority:  ");
                String NoteCategory = ScannerInput.readNextLine("Enter the Note category:  ");

                //pass the index of the noteand the new note details to controllers.Store for updating and check for success.
                if (noteAPI.updateNote(indexToUpdate,Notetitle,NotePriority,NoteCategory)){
                    System.out.println("Update Successful");
                }
                else{
                    System.out.println("Update NOT Successful");
                }
            }
            else{
                System.out.println("There are no notes for this index number");
            }
        }
    }


    private void addItemToNote(){

        printActiveNotes();
        int indexToNote=ScannerInput.readNextInt("Enter the Note Name:  ");
        if(noteAPI.isValidIndex(indexToNote)) {
            String str = ScannerInput.readNextLine("Enter the ItemDescription:  ");
            Item e = new Item(str, false);
            noteAPI.findNote(indexToNote).addItem(e);
            System.out.println("add successfully");
        }else {
        System.out.println("can't add");}
    }


    private void save() {
        try {
            noteAPI.save();
        } catch (Exception e) {
            System.err.println("Error writing to file: " + e);
        }
    }


    private void load() {
        try {
            noteAPI.load();
        } catch (Exception e) {
            System.err.println("Error reading from file: " + e);
        }
    }

}