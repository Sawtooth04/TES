import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./components/Login/Login";
import Registration from "./components/Registration/Registration";

function App() {
  return (
      <Router>
          <Routes>
              <Route path="/login" element={<Login/>}/>
              <Route path="/registration" element={<Registration/>}/>
          </Routes>
      </Router>
  );
}

export default App;
