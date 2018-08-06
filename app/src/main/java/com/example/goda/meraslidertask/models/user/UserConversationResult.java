package com.example.goda.meraslidertask.models.user;

import com.example.goda.meraslidertask.models.login.LoginResults;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ahmed Ali on 8/6/2018.
 */

public class UserConversationResult implements Serializable {

    @SerializedName("current_page")
    @Expose
    private int currentPage;

    @SerializedName("page_limit")
    @Expose
    private int pageLimit;

    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    @SerializedName("total_conversations")
    @Expose
    private int totalConversations;

    @SerializedName("conversations")
    @Expose
    private Conversation[] conversations;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageLimit() {
        return pageLimit;
    }

    public void setPageLimit(int pageLimit) {
        this.pageLimit = pageLimit;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalConversations() {
        return totalConversations;
    }

    public void setTotalConversations(int totalConversations) {
        this.totalConversations = totalConversations;
    }

    public Conversation[] getConversations() {
        return conversations;
    }

    public void setConversations(Conversation[] conversations) {
        this.conversations = conversations;
    }
}
