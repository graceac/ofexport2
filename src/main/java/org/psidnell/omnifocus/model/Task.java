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

import org.psidnell.omnifocus.sqlite.SQLiteProperty;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Task extends Common {

    public static final String TYPE = "Task";

    private String containingProject;
    private boolean next;
    private boolean blocked;
    
    private String parentTaskId;

    private String contextId;
    
    @SQLiteProperty (name="context")
    public String getContextId () {
        return contextId;
    }
    
    public void setContextId (String contextId) {
        this.contextId = contextId;
    }
    
    @SQLiteProperty (name="parent")
    public String getParentTaskId () {
        return parentTaskId;
    }
    
    public void setParentTaskId (String parentFolderId) {
        this.parentTaskId = parentFolderId;
    }

    public String getContainingProject() {
        return containingProject;
    }

    public void setContainingProject(String containingProject) {
        this.containingProject = containingProject;
    }

    @Override
    @JsonIgnore
    public String getType() {
        return TYPE;
    }

    public boolean getNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
