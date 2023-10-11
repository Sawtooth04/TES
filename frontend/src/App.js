import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./components/Login/Login";
import Registration from "./components/Registration/Registration";
import PrivateRoute from "./components/PrivateRoute/PrivateRoute";

function App() {
  return (
      <Router>
          <Routes>
              <Route path="/login" element={<Login/>}/>
              <Route path="/registration" element={<Registration/>}/>
              <Route path="*" element={<PrivateRoute/>}/>
          </Routes>
      </Router>
  );
}

export default App;
