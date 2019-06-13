import React from 'react';
import axios from 'axios';
import { NavLink } from "react-router-dom";

export class AddCourses extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            teachers: [],
            active: true,
            level: "BASICO",
        }

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {

        const newCourse = {
            title: this.state.title,
            level: this.state.level,
            numberOfHours: this.state.numberOfHours,
            teacher: { id: this.state.teacher},
            state: this.state.active,
        };

        if(newCourse.title && newCourse.level  && newCourse.numberOfHours  && newCourse.teacher && newCourse.teacher.id && newCourse.state) {
            this.setState({ messageOk: false, messageError: false });

            axios.post(`http://localhost:8080/api/v1/course`, newCourse)
              .then(res => {
                const courseCreate = res.data;
                this.setState({ 
                    messageOk: true, 
                    courseCreate: courseCreate,
                    active: true,
                    level: "BASICO",
                    teacher: '',
                    title: '',
                    numberOfHours: ''
                 });
    
              }).catch(error => {
                if(error.response.status === 409){
                    this.setState({ messageError: true, message: "El título indicado ya existe" });
                } else{
                    this.setState({ messageError: true, message: "Código de error {error.response.status}" });
                }
                  
              });
        } else {
            this.setState({ messageError: true, message: "Todos los campos son obligatorios" });
        }

        
        event.preventDefault();
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;
        this.setState({
          [name]: value
        });
    }

    componentDidMount() {
        axios.get(`http://localhost:8080/api/v1/teacher`)
          .then(res => {
            const teachers = res.data;
            this.setState({ teachers });
          })
    }

    render() {
        const messageOk = this.state.messageOk;
        const messageError = this.state.messageError;
        let alert;
        if (messageError) {
            alert = <div className="alert alert-danger mt-3">Se ha producido un error: {this.state.message}</div>;
        } else if(messageOk) {
            alert = <div className="alert alert-info mt-3">Curso creado correctamente. <NavLink to="/" exact>Volver al listado</NavLink></div>;
        }

       return (
        <div className="col-6">
            <h2>Añadir Curso</h2>
            <form onSubmit={this.handleSubmit}>
                <div className="form-check">
                    <input className="form-check-input" type="checkbox" id="active" name="active" 
                        checked={this.state.active} onChange={this.handleInputChange}/>
                    <label className="form-check-label" htmlFor="defaultCheck1">
                        Activo
                    </label>
                </div>
                <div className="form-group">
                    <label htmlFor="teacher">Profesor</label>
                    <select id="teacher" className="form-control" value={this.state.teacher}
                         name="teacher" onChange={this.handleInputChange} required>
                        <option>Selecciona un profesor...</option>
                        { this.state.teachers.map(teacher => <option value={teacher.id} key={teacher.id}>{teacher.firstName} {teacher.lastName}</option>)}  
                    </select>
                </div>
                <div className="form-group">
                    <label htmlFor="title">Título</label>
                    <input type="text" className="form-control" id="title" name="title" required
                        placeholder="Titulo del curso" onChange={this.handleInputChange}/>
                </div>
                <div className="form-group">
                    <label htmlFor="level">Nivel</label>
                    <select id="level" className="form-control" value={this.state.level}
                        name="level" onChange={this.handleInputChange} required>
                        <option value="BASICO">Básico</option>
                        <option value="INTERMEDIO">Intermedio</option>
                        <option value="AVANZADO">Avanzado</option>
                    </select>
                </div>
                <div className="form-group">
                    <label htmlFor="numberOfHours">Horas</label>
                    <input type="number" className="form-control" id="numberOfHours" 
                        name="numberOfHours" min={0} onChange={this.handleInputChange} required/>
                </div>
                <button type="submit" className="btn btn-primary">Añadir</button>
                <NavLink to="/" className="ml-3 btn btn-secondary">Cancelar</NavLink>
                {alert}
            </form>
        </div>
       );
    }
}
