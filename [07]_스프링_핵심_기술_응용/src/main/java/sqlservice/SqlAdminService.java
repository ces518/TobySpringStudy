package sqlservice;

import java.util.Map;

public class SqlAdminService {
    private static final String KEY_ID = "KEY_ID";
    private static final String SQL_ID = "SQL_ID";
    private UpdatableSqlRegistry updatableSqlRegistry;

    public void setUpdatableSqlRegistry(UpdatableSqlRegistry updatableSqlRegistry) {
        this.updatableSqlRegistry = updatableSqlRegistry;
    }

    public void updateEventListener(UpdateEvent event) {
        updatableSqlRegistry.updateSql(event.get(KEY_ID), event.get(SQL_ID));
    }

    static class UpdateEvent {

        private Map<String, String> data;

        String get(String key) {
            return data.get(key);
        }
    }
}
