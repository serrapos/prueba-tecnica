import React from 'react';
import axios from 'axios';


export class ListCourses extends React.Component {

    state = {
        courses: []
    }

    componentDidMount() {
        axios.get(`http://localhost:8080/api/v1/course/active`)
          .then(res => {
            const courses = res.data;
            this.setState({ courses });
          })
    }

    render() {
       return (
        <div>
            <h2>Listado de cursos</h2>
            <table className="table">
                <thead className="thead-light">
                    <tr>
                    <th scope="col">#</th>
                    <th scope="col">Título</th>
                    <th scope="col">Profesor</th>
                    <th scope="col">Nivel</th>
                    <th scope="col">Horas</th>
                    </tr>
                </thead>
                <tbody>
                    { this.state.courses.map(course => 
                        <tr key={course.id}>
                        <th scope="row">{course.id}</th>
                        <td>{course.title}</td>
                        <td>{course.teacher.firstName} {course.teacher.lastName}</td>
                        <td>{course.level}</td>
                        <td>{course.numberOfHours}</td>
                        </tr>
                        )}                    
                </tbody>
            </table>
        </div>
       );
    }
 }