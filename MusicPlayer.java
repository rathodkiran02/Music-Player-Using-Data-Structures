package player;

import java.io.*;
import java.util.Scanner;

class Node {
    String song;
    Node next;
    Node prev;

    Node(String song) {
        this.song = song;
        this.next = null;
        this.prev = null;
    }
}

public class MusicPlayer {
    private Node top;
    private Node temp;
    private Node top1;

    public MusicPlayer() {
        this.top = null;
        this.temp = null;
        this.top1 = null;
    }

    public void toFile(String song) {
        File file = new File("playlist.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(song);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNode(Node first) {
        Scanner scanner = new Scanner(System.in);
        while (first.next != null) {
            first = first.next;
        }
        System.out.print("\nEnter Song name: ");
        String song = scanner.nextLine();
        Node newNode = new Node(song);
        first.next = newNode;
        newNode.prev = first;
        toFile(song);
    }

    public void addNodeFromFile(Node first, String song) {
        while (first.next != null) {
            first = first.next;
        }
        Node newNode = new Node(song);
        first.next = newNode;
        newNode.prev = first;
    }

    public void deleteFile(String song) {
        File playlist = new File("playlist.txt");
        File tempFile = new File("temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(playlist));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                if (!line.trim().equals(song)) {
                    writer.write(line);
                    writer.newLine();
                } else {
                    found = true;
                }
            }

            if (found) {
                System.out.println("Song has been deleted.");
            } else {
                System.out.println("There is no song with the name you entered.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!playlist.delete()) {
            System.out.println("Could not delete the file");
        }

        if (!tempFile.renameTo(playlist)) {
            System.out.println("Could not rename the file");
        }
    }

    public void delNode(Node first) {
        while ((first.next).next != null) {
            first = first.next;
        }
        Node temp = first.next;
        first.next = null;
        System.out.println("Deleted");
    }

    public void printList(Node first) {
        System.out.println("\nSongs Name:");
        while (first.next != null) {
            System.out.println(first.song);
            first = first.next;
        }
        System.out.println(first.song);
    }

    public void countNodes(Node first) {
        int count = 0;
        while (first.next != null) {
            first = first.next;
            count++;
        }
        count++;
        System.out.println("\nTotal songs: " + count);
    }

    public Node delPos(Node pointer, int pos) {
        Node prev = null;
        Node temp;
        int i = 0;

        if (pos == 1) {
            temp = pointer;
            deleteFile(temp.song);
            pointer = pointer.next;
            if (pointer != null) {
                pointer.prev = null;
            }
            System.out.println("The list is updated\nUse the display function to check");
            return pointer;
        }

        while (i < pos - 1) {
            prev = pointer;
            pointer = pointer.next;
            i++;
        }

        if (pointer.next == null) {
            temp = pointer;
            deleteFile(temp.song);
            prev.next = null;
        } else {
            temp = pointer;
            deleteFile(temp.song);
            prev.next = temp.next;
            if (temp.next != null) {
                temp.next.prev = prev;
            }
        }
        System.out.println("The list is updated\nUse the display function to check");
        return pointer;
    }

    public void search(Node first) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter song to be searched: ");
        String song = scanner.nextLine();
        boolean found = false;

        while (first != null) {
            if (first.song.equals(song)) {
                System.out.println("\n#Song Found");
                found = true;
                break;
            } else {
                first = first.next;
            }
        }
        if (!found) {
            System.out.println("\n#Song Not found");
        }
    }

    public void push(String data) {
        if (top == null) {
            top = new Node(data);
        } else if (!top.song.equals(data)) {
            temp = new Node(data);
            temp.next = top;
            top = temp;
        }
    }

    public void display() {
        top1 = top;
        if (top1 == null) {
            System.out.println("\n=>NO recently played tracks.");
            return;
        }
        System.out.println("\n#Recently played tracks:");
        while (top1 != null) {
            System.out.println(top1.song);
            top1 = top1.next;
        }
    }

    public void play(Node first) {
        Scanner scanner = new Scanner(System.in);
        printList(first);
        System.out.print("\nChoose the song you wish to play: ");
        String song = scanner.nextLine();
        boolean found = false;

        while (first != null) {
            if (first.song.equals(song)) {
                System.out.println("\n=>Now Playing......" + song);
                found = true;
                push(song);
                break;
            } else {
                first = first.next;
            }
        }
        if (!found) {
            System.out.println("\n#Song Not found");
        }
    }

    public void recent() {
        display();
    }

    public void topElement() {
        if (top == null) {
            System.out.println("\n#NO last played tracks.");
            return;
        }
        System.out.println("\n=>Last Played Song - " + top.song);
    }

    public void sort(Node pointer) {
        Node a = pointer, b, c, e = null, tmp;
        while (e != pointer.next) {
            c = a = pointer;
            b = a.next;
            while (a != e) {
                if (a.song.compareTo(b.song) > 0) {
                    if (a == pointer) {
                        tmp = b.next;
                        b.next = a;
                        a.next = tmp;
                        pointer = b;
                        c = b;
                    } else {
                        tmp = b.next;
                        b.next = a;
                        a.next = tmp;
                        c.next = b;
                        c = b;
                    }
                } else {
                    c = a;
                    a = a.next;
                }
                b = a.next;
                if (b == e) {
                    e = a;
                }
            }
        }
    }

    public void addPlaylist(Node start) {
        File file = new File("playlist.txt");
        if (!file.exists()) {
            System.out.println("No existing playlist file found. A new file will be created when you add songs.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                addNodeFromFile(start, line);
            }
            System.out.println("Playlist Added");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delSearch(Node start) {
        Scanner scanner = new Scanner(System.in);
        printList(start);
        System.out.print("\nChoose song you wish to delete: ");
        String song = scanner.nextLine();
        boolean found = false;

        while (start != null) {
            if (start.song.equals(song)) {
                System.out.println("\n#Song Found");
                deleteFile(start.song);
                if (start.prev != null) {
                    start.prev.next = start.next;
                }
                if (start.next != null) {
                    start.next.prev = start.prev;
                }
                found = true;
                break;
            } else {
                start = start.next;
            }
        }
        if (!found) {
            System.out.println("\n#Song Not found");
        }
    }

    public void deleteMenu(Node start) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which type of delete do you want?\n1.By Search\n2.By Position");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                delSearch(start);
                break;
            case 2:
                System.out.print("Enter the position of the song: ");
                int pos = scanner.nextInt();
                start = delPos(start, pos);
                break;
        }
    }

    public static void main(String[] args) {
        MusicPlayer musicPlayer = new MusicPlayer();
        Scanner scanner = new Scanner(System.in);

        Node start = new Node("");
        Node hold = start;

        System.out.println("\t\t\t**WELCOME**");
        System.out.println("\n**Please use '_' for space.");

        String playlistName;
        while (true) {
            System.out.print("\n\nEnter your playlist name: ");
            playlistName = scanner.nextLine().trim();
            if (!playlistName.isEmpty()) {
                break;
            }
            System.out.println("Playlist name cannot be empty. Please enter a valid name.");
        }

        while (true) {
            System.out.println("\n1. Add New Song\n2. Delete Song\n3. Display Entered Playlist\n4. Total Songs\n5. Search Song\n6. Play Song\n7. Recently Played List\n8. Last Played\n9. Sorted playlist\n10. Add From File\n11. Exit");
            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    musicPlayer.addNode(hold);
                    break;
                case 2:
                    musicPlayer.deleteMenu(hold);
                    break;
                case 3:
                    System.out.println("\nPlaylist Name: " + playlistName);
                    if (hold.next == null) {
                        System.out.println("The playlist is empty.");
                    } else {
                        musicPlayer.printList(hold.next);
                    }
                    break;
                case 4:
                    if (hold.next == null) {
                        System.out.println("The playlist is empty.");
                    } else {
                        musicPlayer.countNodes(hold.next);
                    }
                    break;
                case 5:
                    if (hold.next == null) {
                        System.out.println("The playlist is empty.");
                    } else {
                        musicPlayer.search(hold.next);
                    }
                    break;
                case 6:
                    if (hold.next == null) {
                        System.out.println("The playlist is empty.");
                    } else {
                        musicPlayer.play(hold.next);
                    }
                    break;
                case 7:
                    musicPlayer.recent();
                    break;
                case 8:
                    musicPlayer.topElement();
                    break;
                case 9:
                    if (hold.next == null) {
                        System.out.println("The playlist is empty.");
                    } else {
                        musicPlayer.sort(hold.next);
                        musicPlayer.printList(hold.next);
                    }
                    break;
                case 10:
                    musicPlayer.addPlaylist(hold);
                    break;
                case 11:
                    System.exit(0);
            }
        }
    }
}
