import React from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import 'primereact/resources/themes/nova-light/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import { BrowserRouter as Router, Route } from "react-router-dom";
import { Header } from './components/layouts';
import { ListCourses } from './components/app/courses/ListCourses';
import { AddCourses } from './components/app/courses/AddCourses';


function App() {
  return (
    <div className="App">
      <Router>
        <Header></Header>
        <main role="main" className="container">
          <Route path="/" exact component={ListCourses} />
          <Route path="/new-course/" component={AddCourses} />
        </main>
      </Router>
    </div>
  );
}

export default App;
