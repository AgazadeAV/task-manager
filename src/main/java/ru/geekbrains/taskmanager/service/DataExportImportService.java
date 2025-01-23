package ru.geekbrains.taskmanager.service;

import java.io.IOException;

public interface DataExportImportService {

    void exportTasks(String filePath) throws IOException;

    void exportProjects(String filePath) throws IOException;

    void importTasks(String filePath) throws IOException;

    void importProjects(String filePath) throws IOException;

    void exportUsers(String filePath) throws IOException;

    void importUsers(String filePath) throws IOException;
}
