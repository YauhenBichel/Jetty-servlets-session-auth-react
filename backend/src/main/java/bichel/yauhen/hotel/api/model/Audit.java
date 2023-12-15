package bichel.yauhen.hotel.api.model;

import java.time.LocalDateTime;

/**
 * Model for audit
 */
public class Audit {
    private LocalDateTime created;
    private LocalDateTime modified;

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Audit)) return false;

        Audit audit = (Audit) o;

        if (created != null ? !created.equals(audit.created) : audit.created != null) return false;
        return modified != null ? modified.equals(audit.modified) : audit.modified == null;
    }

    @Override
    public int hashCode() {
        int result = created != null ? created.hashCode() : 0;
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        return result;
    }
}
