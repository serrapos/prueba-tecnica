import React from 'react';
import axios from 'axios';
import {DataTable} from 'primereact/datatable';
import {Column} from 'primereact/column';
import { NavLink } from "react-router-dom";

export class ListCourses extends React.Component {

    constructor() {
        super();
        this.state = {
            courses: []
        };
        this.teacherTemplate = this.teacherTemplate.bind(this);
        this.fileTemplate = this.fileTemplate.bind(this);
    }
    
    componentDidMount() {
        axios.get(`http://localhost:8080/api/v1/course/active`)
          .then(res => {
            const courses = res.data;
            this.setState({ courses });
          })
    }

    teacherTemplate(rowData, column) {
        return rowData.teacher.lastName + ", " + rowData.teacher.firstName;
    }

    fileTemplate(rowData, column) {
        if(rowData.fileId){
            const url = "http://localhost:8080/api/v1/filetopics/"+rowData.fileId
            return <a href={url} target="_blank" download>Descargar</a>
        } 
        return "No hay fichero adjunto";
    }

    render() {
       return (
        <div>
            <h2>Listado de cursos</h2>
            <div className="text-right p-3"><NavLink to="/new-course/" className="btn btn-primary btn-lg">Nuevo</NavLink></div>
            <DataTable value={this.state.courses} paginator={true} paginatorLeft={true} paginatorRight={true} rows={5} rowsPerPageOptions={[5,10,20]}>
                <Column field="title" header="Título" sortable={true}/>
                <Column field="teacher" body={this.teacherTemplate}  header="Profesor" sortable={true} sortField="teacher.lastName"/>
                <Column field="level" header="Nivel" sortable={true} />
                <Column field="numberOfHours" header="Horas" sortable={true}/>
                <Column field="fileId" header="Temario" body={this.fileTemplate}/>
            </DataTable>
        </div>
       );
    }
 }