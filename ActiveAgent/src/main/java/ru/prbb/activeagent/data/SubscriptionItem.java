/**
 *
 */
package ru.prbb.activeagent.data;

import java.io.Serializable;

/**
 * @author RBr
 *
 */
public class SubscriptionItem implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String RUNNING = "Running";
    public static final String STOPPED = "Stopped";

    private Long id;
    private String name;
    private String comment;
    private String status;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        return (id == null) ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SubscriptionItem other = (SubscriptionItem) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SubscriptionItem [id=" + id + ", name=" + name + ", comment="
                + comment + ", status=" + status + "]";
    }

}
