package second;

public class Book {
private int isbn;
private String author;
private String title;
public int getIsbn() {
	return isbn;
}
public void setIsbn(int isbn) {
	this.isbn = isbn;
}
public String getAuthor() {
	return author;
}
public void setAuthor(String author) {
	this.author = author;
}
public String getTitle() {
	return title;
}
@Override
public String toString() {
	return "Book [isbn=" + isbn + ", author=" + author + ", title=" + title
			+ "]";
}
public void setTitle(String title) {
	this.title = title;
}
}
