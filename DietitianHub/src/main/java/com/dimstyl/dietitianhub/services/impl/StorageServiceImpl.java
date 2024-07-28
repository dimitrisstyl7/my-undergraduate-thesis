package com.dimstyl.dietitianhub.services.impl;

import com.dimstyl.dietitianhub.exceptions.DirectoryCreationException;
import com.dimstyl.dietitianhub.exceptions.FileDeletionException;
import com.dimstyl.dietitianhub.exceptions.FileStorageException;
import com.dimstyl.dietitianhub.exceptions.StorageFileNotFoundException;
import com.dimstyl.dietitianhub.services.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path DIET_PLANS_DIRECTORY;

    public StorageServiceImpl() {
        // Directory: /{project-root}/../diet-plans
        DIET_PLANS_DIRECTORY = Paths.get("").toAbsolutePath().getParent().resolve("diet-plans");
        File dietPlansDirectoryFile = DIET_PLANS_DIRECTORY.toFile();

        // Create the "diet-plans" directory if it does not exist
        if (!dietPlansDirectoryFile.exists()) {
            boolean created = dietPlansDirectoryFile.mkdir();
            if (!created) {
                throw new DirectoryCreationException("Failed to create diet plans directory.");
            }
        }
    }

    @Override
    public void storeDietPlanFile(String fileName, MultipartFile file) {
        // File path: /{DIET_PLANS_DIRECTORY}/{fileName}
        Path destinationFile = DIET_PLANS_DIRECTORY.resolve(fileName);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileStorageException("Failed to store file: %s".formatted(fileName), e);
        }
    }

    @Override
    public Resource loadDietPlanFileAsResource(String fileName) {
        try {
            Path file = DIET_PLANS_DIRECTORY.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: %s".formatted(fileName));
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: %s".formatted(fileName), e);
        }
    }

    @Override
    public void deleteDietPlanFile(String fileName) {
        // File path: /{DIET_PLANS_DIRECTORY}/{fileName}
        Path destinationFile = DIET_PLANS_DIRECTORY.resolve(fileName);

        try {
            Files.delete(destinationFile);
        } catch (IOException e) {
            throw new FileDeletionException("Failed to delete file: %s".formatted(fileName), e);
        }
    }

}