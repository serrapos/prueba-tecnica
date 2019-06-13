import React from 'react';
import axios from 'axios';
import { NavLink } from "react-router-dom";
import {Spinner} from 'primereact/spinner';
import {Checkbox} from 'primereact/checkbox';
import {Dropdown} from 'primereact/dropdown';

export class AddCourses extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            teachersOptions: [],
            active: true,
            level: "BASICO",
        }

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {

        console.log(this.state);
        const newCourse = {
            title: this.state.title,
            level: this.state.level,
            numberOfHours: this.state.numberOfHours,
            teacher: { id: this.state.teacher},
            state: this.state.active,
        };

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

          }, error => {
            this.setState({ messageError: true });
          })
        event.preventDefault();
    }

    handleInputChange(event) {
        console.log(event);
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;
        console.log("value=",value);
        console.log("name=",name);
        this.setState({
          [name]: value
        });
    }

    componentDidMount() {
        axios.get(`http://localhost:8080/api/v1/teacher`)
          .then(res => {
            const teachersOptions = res.data;
            teachersOptions.map(teacher => {
                return {name: '{teacher.firstName} {teacher.lastName}', code: '{teacher.id}'}
            });
            this.setState({ teachersOptions });
          })
    }

    render() {
        const messageOk = this.state.messageOk;
        const messageError = this.state.messageError;
        let alert;
        if (messageError) {
            alert = <div className="alert alert-danger">Se ha producido un error, revise todos los campos</div>;
        } else if(messageOk) {
            alert = <div className="alert alert-info">Curso creado correctamente. <NavLink className="nav-link" to="/" exact>Volver al listado</NavLink></div>;
        }

       return (
        <div className="col-6">
            <h2>Añadir Curso</h2>
            <form onSubmit={this.handleSubmit}>
                <div className="content-section implementation">
                    <div className="form-check">
                        <Checkbox inputId="active" onChange={e => this.setState({active: e.checked})} checked={this.state.active}></Checkbox>
                        <label className="p-checkbox-label" htmlFor="active">
                            Activo
                        </label>
                    </div>
                    <div className="form-group">
                        <h5>Profesor</h5>
                        <Dropdown required={true} inputId="teacher" value={this.state.teacher} options={this.state.teachersOptions} onChange={(e) => {this.setState({teacher: e.value})}} placeholder="Selecciona un profesor"/>
                        
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
                        <Spinner className="form-control" value={this.state.numberOfHours} onChange={(e) => this.setState({value: e.value})} min={0} />
                    </div>
                    <button type="submit" className="btn btn-primary">Añadir</button>
                </div>
                
                {alert}
            </form>
        </div>
       );
    }
}
