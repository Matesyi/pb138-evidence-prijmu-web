package cz.muni.fi.pb138.evidence.entities;

import java.util.List;

/**
 *
 * @author L
 */
public interface WorkManager {
    
    public void createWork(Work work);
    public void updateWork(Work work);
    public void deleteWork(Work work);
    public Work getWorkById(int id);
    public List<Work> findAllWorks();
    
}
