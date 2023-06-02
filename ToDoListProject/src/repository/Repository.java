package repository;

import java.util.List;
import java.util.Map;

public interface Repository <T> {
	
void add(T t);

void update(T t);

List<T> find(Map<String,String> fieldList);

List<T> findAll();
boolean remove(T t);
}
