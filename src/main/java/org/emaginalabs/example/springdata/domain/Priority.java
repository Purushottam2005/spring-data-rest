package org.emaginalabs.example.springdata.domain;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.Set;
import java.util.HashSet;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

@javax.persistence.Entity
public class Priority extends AbstractPersistable<Integer>

{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	@javax.persistence.Column(nullable = false)
	protected String descripcion;
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	@javax.persistence.OneToMany(mappedBy = "priority")
	protected Set<Todo> todo;


	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public Priority(){
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	public String getDescripcion() {
		return this.descripcion;	
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	public Set<Todo> getTodo() {
		if(this.todo == null) {
				this.todo = new HashSet<Todo>();
		}
		return (Set<Todo>) this.todo;	
	}
	

	

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	public void removeAllTodo(Set<Todo> newTodo) {
		if(this.todo == null) {
			return;
		}
		
		this.todo.removeAll(newTodo);	
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	public void setDescripcion(String myDescripcion) {
		this.descripcion = myDescripcion;	
	}
	

	
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	public void unsetDescripcion() {
		this.descripcion = "";	
	}

	
}

