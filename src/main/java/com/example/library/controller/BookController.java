package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")  // Allow frontend calls
public class BookController {
    private final BookRepository repo;

    public BookController(BookRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return repo.findAll();
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return repo.save(book);
    }

    @PutMapping("/{id}/borrow")
    public Book borrowBook(@PathVariable Long id) {
        Book book = repo.findById(id).orElseThrow();
        book.setBorrowed(true);
        return repo.save(book);
    }

    @PutMapping("/{id}/return")
    public Book returnBook(@PathVariable Long id) {
        Book book = repo.findById(id).orElseThrow();
        book.setBorrowed(false);
        return repo.save(book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        repo.deleteById(id);
    }

    // 🔍 New: Get Book by ID
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    // 🔍 New: Get Book by Title (Search)
    @GetMapping("/search")
    public List<Book> getBookByTitle(@RequestParam String title) {
        return repo.findByTitleContainingIgnoreCase(title);
    }
}
