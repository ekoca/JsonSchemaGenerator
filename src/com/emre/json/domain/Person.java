package com.emre.json.domain;

public class Person {
		public String name;
		public int age;
		  // NOTE: if using getters/setters, can keep fields `protected` or `private`
		@Override
		public String toString() {
			return "MyValue [name=" + name + ", age=" + age + "]";
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
}
