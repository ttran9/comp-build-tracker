import React, { Component } from "react";
import BuildNote from "./BuildNote";

class BuildNoteList extends Component {
  render() {
    const { buildNotes } = this.props;
    return (
      <div className="col-md-12 text-center">
        <h2>Build Notes:</h2>
        <table class="table table-dark table-bordered">
          <thead>
            <tr>
              <th scope="col">Description</th>
            </tr>
          </thead>
          <tbody>
            {buildNotes.map(buildNote => {
              return <BuildNote key={buildNote.id} buildNote={buildNote} />;
            })}
          </tbody>
        </table>
      </div>
    );
  }
}

export default BuildNoteList;
