/*
Copyright 2014 Paul Sidnell

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package org.psidnell.omnifocus.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class DataCache {
    
    private HashMap<String, Folder> folders;
    private HashMap<String, ProjectInfo> projInfos;
    private HashMap<String, Task> tasks;
    private HashMap<String, Context> contexts;
    private HashMap<String, Project> projects;

    public DataCache (
            Collection<Folder> folders,
            Collection<ProjectInfo> projInfos,
            Collection<Task> tasks,
            Collection<Context> contexts) {
        
        this.folders = new HashMap<>();
        for (Folder folder : folders) {
            this.folders.put(folder.getId(), folder);
        }
        
        this.projInfos = new HashMap<>();
        for (ProjectInfo projInfo : projInfos) {
            this.projInfos.put(projInfo.getRootTaskId(), projInfo);
        }
        
        this.tasks = new HashMap<>();
        for (Task task : tasks) {
            this.tasks.put(task.getId(), task);
        }
        
        this.contexts = new HashMap<>();
        for (Context context : contexts) {
            this.contexts.put(context.getId(), context);
        }
        this.projects = new HashMap<>();
        build();
    }
    
    private final void build() {
        
        // Create Projects from their root tasks
        for (ProjectInfo projInfo : projInfos.values()) {
            Task rootTask = tasks.get(projInfo.getRootTaskId());
            Project project = new Project (rootTask);
            projects.put (project.getId (), project);
        }
        
        // Build Folder Hierarchy
        for (Folder folder : folders.values()) {
            String parentId = folder.getParentFolderId();
            if (parentId != null) {
                Folder parent = folders.get(parentId);
                parent.getFolders().add(folder);
            }
        }
        
        // Build Task Hierarchy
        for (Task task : tasks.values()) {
            String parentId = task.getParentTaskId();
            if (parentId != null) {
                Task parent = tasks.get(parentId);
                parent.getTasks().add(task);
            }
        }
        
        // Build Context Hierarchy
        for (Context context : contexts.values()) {
            String parentId = context.getParentContextId();
            if (parentId != null) {
                Context parent = contexts.get(parentId);
                System.out.println ("Knitting " + context.getName() + " and " + parent.getName());
                parent.getContexts().add(context);
            }
        }
       
        // Add tasks to contexts
        for (Task task : tasks.values()) {
            String contextId = task.getContextId();
            if (contextId != null) {
                Context context = contexts.get(contextId);
                context.getTasks().add(task);
            }
        }
    }

    public HashMap<String, Folder> getFolders() {
        return folders;
    }

    public HashMap<String, ProjectInfo> getProjInfos() {
        return projInfos;
    }

    public HashMap<String, Task> getTasks() {
        return tasks;
    }

    public HashMap<String, Context> getContexts() {
        return contexts;
    }

   
}
